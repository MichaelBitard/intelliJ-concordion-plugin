import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;

public class ConcordionAnnotatorTest extends LightCodeInsightFixtureTestCase {
    @Override
    protected String getTestDataPath() {
        return "../concordion/testData";
    }

    public void testAnnotation() {
        myFixture.configureByFiles("AClassicClass.java", "ConcordionTest.java", "concordion.html");
        myFixture.checkHighlighting(false, false, true);
    }
}