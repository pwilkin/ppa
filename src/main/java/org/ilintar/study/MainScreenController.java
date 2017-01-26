package org.ilintar.study;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.ilintar.study.question.*;
import org.ilintar.study.question.event.QuestionAnsweredEvent;
import org.ilintar.study.question.event.QuestionAnsweredEventListener;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class MainScreenController implements QuestionAnsweredEventListener {

    protected PrintWriter out;
	private int whichQuestion;
	private String fileName;
	protected Map<String, Answer> answers;

	public MainScreenController() throws FileNotFoundException {
        out = new PrintWriter("answers.answ");
        answers = new LinkedHashMap<>();
		this.whichQuestion = -1;
	}

	private static Map<String, QuestionFactory> factoryMap;
	static {
		factoryMap = new HashMap<>();
		factoryMap.put("radio", new RadioQuestionFactory());
		factoryMap.put("music", new MusicRadioQuestionFactory());
		factoryMap.put("image", new ImageRadioQuestionFactory());
	}

	@FXML AnchorPane mainStudy;
	@FXML Label fileNameLabel; 
	@FXML TitledPane titledPane;

    public IQuestion currentQuestion;

	int trackNumber = 1; // it's weird place for this var, but it don't destroy anything

	@FXML public void startStudy() {
		if (fileName == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Jest error");
			alert.setHeaderText("Brak pliku");
			alert.setContentText("Proszę wybrać plik z pytaniami");
			alert.showAndWait();
		} else {
			displayQuestion();
	    }
	}
	
	@FXML public void displayQuestion() {
        whichQuestion++;
		mainStudy.getChildren().clear();
		Node questionComponent = readQuestionFromFile(whichQuestion, getClass().getResourceAsStream(fileName));
		if (questionComponent != null){
			String questionTitle = "Pytanie " + String.valueOf(whichQuestion+1);
			titledPane.setText(questionTitle);
            mainStudy.getChildren().add(questionComponent);
        }
        else {
            endStudy();
        }

	}
	


    private void endStudy() {
        mainStudy.getChildren().clear();
        mainStudy.getChildren().add(new Label("Thank you!"));
        saveAnswersToFile();
    }

    private Node readQuestionFromFile(int i, InputStream resourceAsStream) {
		BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream));
		String currentLine;
		int which = 0;
		List<String> questionLines = new ArrayList<>();
		boolean readingQuestions = false;
		String questionType = null;
		String questionId = null;
		try {
			while ((currentLine = br.readLine()) != null) {
				if (currentLine.startsWith("StartQuestion")) { // begin reading questions
					if (readingQuestions) {
						throw new IllegalArgumentException("Invalid file format: StartQuestion without EndQuestion");
					}
					if (which == i) {
						readingQuestions = true;
						String[] elements = currentLine.split(" ");
						if (elements.length > 1) {
							String[] givenType = elements[1].split("=");
							if (givenType.length > 1) {
								questionType = givenType[1];
							}
							if (elements.length > 2){
								String[] givenID = elements[2].split("=");
								questionId = givenID[1];
							}
						}
						if (questionType == null) {
							throw new IllegalArgumentException("Invalid file format: StartQuestion type=<type>");
						}
						if (questionId == null) {
							throw new IllegalArgumentException("Invalid file format: StartQuestion ID=<ID>");
						}
					} else {
						which++;
					}
				} else {
					if (readingQuestions) {
						if (currentLine.startsWith("EndQuestion")) {
							if (factoryMap.containsKey(questionType)) {
								currentQuestion = factoryMap.get(questionType).createQuestion(questionLines, questionId);
								if (questionType.equals("music")){
                                    MusicRadioQuestion musicQuestion = (MusicRadioQuestion) currentQuestion;
                                    musicQuestion.runTrack(trackNumber) ;
									trackNumber++;
								}
								currentQuestion.addQuestionAnsweredListener(this);
								return currentQuestion.getRenderedQuestion();
							} else {
								throw new IllegalArgumentException("Do not have a factory for question type: " + questionType);
							}
						} else {						
							questionLines.add(currentLine.trim());
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void handleEvent(QuestionAnsweredEvent event) {
		IQuestion question = event.getQuestion(); // KS: this line!
		Answer answer = event.getAnswer();
		System.out.println(question.getId());
		System.out.println(answer.getAnswer());
        event.saveToFile();
        answers.put(question.getId(), answer);
        question.cleanup();
        displayQuestion();
	}

	public void chooseFile() {
		 FileChooser fileChooser = new FileChooser();
		 String currentDir = System.getProperty("user.dir") + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "org" + File.separator + "ilintar" + File.separator + "study"+ File.separator;
         File file = new File(currentDir); // Should open in our working directory by default now.
         fileChooser.setInitialDirectory(file);
		 fileChooser.setTitle("Open Resource File");
		 fileChooser.getExtensionFilters().addAll(
				 new ExtensionFilter("Question Files", "*.sqf"),
		         new ExtensionFilter("Text Files", "*.txt"),
		         new ExtensionFilter("All Files", "*.*"));
		 File selectedFile = fileChooser.showOpenDialog(mainStudy.getScene().getWindow());
		 if (selectedFile != null){
			 fileName = selectedFile.getName();
			 fileNameLabel.setText(fileName);
	}}

	public void saveAnswersToFile() {

		DocumentBuilderFactory factory = null;
		DocumentBuilder builder = null;
		org.w3c.dom.Document xml;

		try {
			factory = DocumentBuilderFactory.newInstance();
			builder = factory.newDocumentBuilder();
			xml = builder.newDocument();
			Element ans = xml.createElement("answers");
			xml.appendChild(ans);
			for (Entry<String, Answer> answer : answers.entrySet()) {
				Element ansXML = xml.createElement("answer");
				ans.appendChild(ansXML);
				ansXML.setAttribute("question", answer.getKey());
				ansXML.setTextContent(answer.getValue().getAnswer());
			}
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(xml);
			StreamResult result = new StreamResult(new File("answers.xml"));
			transformer.transform(source, result);
		} catch (ParserConfigurationException | TransformerException e) {
			e.printStackTrace();
		}


		try (PdfDocument pdf = new PdfDocument(new PdfWriter("answers.pdf"))) {
			Document document = new Document(pdf);
			PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
			PdfFont bold = PdfFontFactory.createFont(FontConstants.TIMES_BOLD);
			for (Entry<String, Answer> answer : answers.entrySet()) {
				Text title = new Text(answer.getKey()).setFont(bold);
				Text author = new Text(answer.getValue().getAnswer()).setFont(font);
				Paragraph p = new Paragraph().add(title).add(": ").add(author);
				document.add(p);
			}
			document.close();
		} catch (IOException e) {
			System.err.println("IOException: " + e.getMessage());
		}

	}
	
}
