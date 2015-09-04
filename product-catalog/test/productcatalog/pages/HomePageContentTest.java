package productcatalog.pages;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HomePageContentTest {

    @Test
    public void hasCorrectTitle() {
        final HomePageContent homePageContent = new HomePageContent();

        assertThat(homePageContent.additionalTitle()).isEqualTo("Home");
    }
}
