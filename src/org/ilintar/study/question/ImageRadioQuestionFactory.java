package org.ilintar.study.question;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ImageRadioQuestionFactory extends RadioQuestionFactory {

    @Override
    public RadioQuestion createQuestion(List<String> lines, String id) {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        final ImageView imv = new ImageView();
        imv.setPreserveRatio(true);
        imv.setFitHeight(150);
        imv.setFitWidth(150);
        final Image image2 = new Image(getClass().getResourceAsStream(lines.get(0)));
        imv.setImage(image2);

        final HBox pictureRegion = new HBox();

        pictureRegion.getChildren().add(imv);
        gridPane.add(pictureRegion, 1, 1);



        VBox radioQuestion = new VBox();
        String question1 = lines.get(1);
        radioQuestion.getChildren().add(new Label(question1));
//	        String labelId = "id= " + id; // KZ: it definitely does not look like well in the user view
//	        questions.getChildren().add(new Label(labelId));
        ToggleGroup group = new ToggleGroup();

        for (int i = 2; i < lines.size(); i+=2) {
            String answer = lines.get(i);
            String answerCode = lines.get(i+1);
            RadioButton button = new RadioButton(answer);
            button.setUserData(answerCode);
            button.setToggleGroup(group);
            radioQuestion.getChildren().add(button);
        }


        Button finishButton = new Button("Submit");
        radioQuestion.getChildren().add(finishButton);
        gridPane.add(radioQuestion,3,1);
        ImageRadioQuestion question = new ImageRadioQuestion(gridPane, id, group);
        finishButton.setOnAction((event) -> {question.fireEvent();});
        //??radioQuestion.onContextMenuRequestedProperty();
        return question;
    }


}
