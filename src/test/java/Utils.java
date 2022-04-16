import java.util.List;

import static com.codeborne.selenide.Selenide.$$;

public class Utils {
    public static List<String> getSubmenuText() {
        return $$(".js-catalog-submenu-item").texts();
    }
}
