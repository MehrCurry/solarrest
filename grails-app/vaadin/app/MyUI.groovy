package app

import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import com.vaadin.server.VaadinRequest
import com.vaadin.ui.Label
import com.vaadin.grails.Grails
import org.joda.time.DateTime
import solarrest.SolarLogService

/**
 *
 *
 * @author
 */
class MyUI extends UI {
    public static final String URL = 'http://home11.solarlog-web.de/6764.html?file='

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        def service=Grails.get(SolarLogService)
        service.getMinDay(URL, DateTime.now())
        // service.getAllData(URL)

		VerticalLayout layout = new VerticalLayout()

        String homeLabel = Grails.i18n("default.home.label")
        Label label = new Label(homeLabel)
        layout.addComponent(label)

        // example of how to get Grails service and call a method
        // List<User> users = Grails.get(UserService).getListOfUsers()
        //    for (User user : users) {
        //    	layout.addComponent(new Label(user.name))
        // }

		setContent(layout)
    }
}
