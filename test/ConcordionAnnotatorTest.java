import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;

public class ConcordionAnnotatorTest extends LightCodeInsightFixtureTestCase {
    @Override
    protected String getTestDataPath() {
        return "../intelliJ-concordion-plugin/testData";
    }

    public void testAnnotation_notAConcordionClass() {
        myFixture.configureByFiles("AClassicClass.java", "concordion.html");
        myFixture.checkHighlighting(false, false, true);
    }

    public void testAnnotation_theMainConcordionClass() {
        myFixture.configureByFiles("ConcordionTest.java", "concordion.html");
        myFixture.checkHighlighting(false, false, true);
    }

    public void testAnnotation_doNotCheckPrivate() {
        myFixture.configureByFiles("ConcordionWithPrivateTest.java", "concordion.html");
        myFixture.checkHighlighting(false, false, true);
    }

    //FIXME IMPOSSIBLE TO TEST?
    //public void testAnnotation_aChildOfAConcordionClass() {
    //    myFixture.configureByFiles("AChildOfConcordionTest.java", "ConcordionTest.java", "concordion.html");
    //    myFixture.checkHighlighting(false, false, true);
    //}
}