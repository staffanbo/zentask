/**
 * Created with IntelliJ IDEA.
 * User: stabor
 * Date: 2013-03-08
 * Time: 10:15
 * To change this template use File | Settings | File Templates.
 */

import play.*;
import play.libs.*;
import com.avaje.ebean.Ebean;
import models.*;
import java.util.*;

public class Global extends GlobalSettings {
    @Override
    public void onStart(Application app) {
        // Check if the database is empty
        if (User.find.findRowCount() == 0) {
            Map<String,List<Object>> all = (Map<String,List<Object>>)Yaml.load("initial-data.yml");

            // Insert users first
            Ebean.save(all.get("users"));

            // Insert projects
            Ebean.save(all.get("projects"));
            for(Object project: all.get("projects")) {
                // Insert the project/user relation
                Ebean.saveManyToManyAssociations(project, "members");
            }

            // Insert tasks
            Ebean.save(all.get("tasks"));
        }
    }
}
