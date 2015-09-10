package productcatalog.pages;

import common.pages.LinkData;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HomePageContentTest {

    @Test
    public void hasCorrectTitle() {
        final HomePageContent homePageContent = new HomePageContent(new LinkData("", ""), new LinkData("", ""));

        assertThat(homePageContent.additionalTitle()).isEqualTo("Home");
    }
}
