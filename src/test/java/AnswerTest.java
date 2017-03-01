import javafx.application.Application;
import javafx.stage.Stage;
import org.ilintar.study.question.RadioQuestion;
import org.ilintar.study.question.RadioQuestionFactory;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pwilkin on 01-Mar-17.
 */
public class AnswerTest {

    public static class MockApp extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            // noop
        }
    }

    @BeforeClass
    public static void initJFX() {
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(MockApp.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
    }

    private RadioQuestion createMockQuestion() {
        RadioQuestionFactory rqf = new RadioQuestionFactory();
        List<String> lines = new ArrayList<>();
        lines.add("The Question");
        lines.add("First answer");
        lines.add("FA");
        lines.add("Second answer");
        lines.add("SA");
        return rqf.createQuestion(lines, "LINES");
    }

    @Test
    public void testRadioCreated() {
        RadioQuestion q = createMockQuestion();
        Assert.assertNotNull(q);
    }

    @Test
    public void testRadioNoDefaultAnswer() {
        RadioQuestion q = createMockQuestion();
        Assert.assertNull(q.getAnswer());
    }

}
