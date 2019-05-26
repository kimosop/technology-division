import models.Departments;
import models.Staff;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;
import static spark.Spark.*;
import java.util.Map;

public class App {
    public static void main(String[] args) {


        staticFileLocation("/public");

        //FOR HEROKU
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 4567;
        }

        setPort(port);

        //        Link to Staff
        get("/staff", (request, response) -> {
            return new ModelAndView(new HashMap(), "staff.hbs");
        }, new HandlebarsTemplateEngine());

        //        Link to index
        get("/index", (request, response) -> {
            return new ModelAndView(new HashMap(), "welcome.hbs");
        }, new HandlebarsTemplateEngine());

        //        Link to Department
        get("/departments", (request, response) -> {
            return new ModelAndView(new HashMap(), "departments.hbs");
        }, new HandlebarsTemplateEngine());

        //        Link to Staff-Details
        get("/staff-details", (request, response) -> {
            return new ModelAndView(new HashMap(), "staff-details.hbs");
        }, new HandlebarsTemplateEngine());

        //Creating new Stuff
        post("/staffs/new", (request, response) -> { //URL to make new post on POST route
            Map<String, Object> model = new HashMap<String, Object>();
            String content = request.queryParams("content");
            String department = request.queryParams("department");
            Staff newStaff = new Staff(content,department);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show new staff form
        get("/staffs/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "welcome.hbs");
        }, new HandlebarsTemplateEngine());


        //Creating new Department
        post("/departments/new", (request, response) -> { //URL to make new post on POST route
            Map<String, Object> model = new HashMap<String, Object>();
            String name = request.queryParams("name");
            Departments newDepartment= new Departments(name);
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show new department form
        get("/departements/new", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return new ModelAndView(model, "welcome.hbs");
        }, new HandlebarsTemplateEngine());

        //Shows all Staff at Welcome
        get("/", (req, res) -> {


            Map<String, Object> model = new HashMap<>();
            ArrayList<Staff> staffs = Staff.getAll();

            ArrayList<Departments> departments = Departments.getAll();
            model.put("departments", departments);
            model.put("staffs", staffs);
            return new ModelAndView(model, "welcome.hbs");
        }, new HandlebarsTemplateEngine());

        //Shows all Staff at Welcome
        get("/staff", (req, res) -> {

            Staff Jane = new Staff("Jane Doe", "Planning");
            Departments Optimization = new Departments("Optimization");
            Departments Planning = new Departments("Planning");

            Map<String, Object> model = new HashMap<>();
            ArrayList<Staff> staffs = Staff.getAll();
            ArrayList<Departments> departments = Departments.getAll();
            model.put("departments", departments);
            model.put("staffs", staffs);
            return new ModelAndView(model, "staff.hbs");
        }, new HandlebarsTemplateEngine());

        //Shows all Staff at Staff
        get("/", (req, res) -> {

            Staff Jane = new Staff("Jane Doe", "Planning");
            Staff John = new Staff("John Doe", "Optimization");

            Map<String, Object> model = new HashMap<>();
            ArrayList<Staff> staffs = Staff.getAll();
            model.put("staffs", staffs);
            return new ModelAndView(model, "staff.hbs");
        }, new HandlebarsTemplateEngine());

        //Shows all Departments at Welcome
        get("/", (req, res) -> {

            Departments Optimization = new Departments("Optimization");
            Departments Planning = new Departments("Planning");

            Map<String, Object> model = new HashMap<>();
            ArrayList<Departments> departments = Departments.getAll();
            model.put("departments", departments);
            return new ModelAndView(model, "welcome.hbs");
        }, new HandlebarsTemplateEngine());

        //get: delete an individual staff
        get("/staffs/:id/delete", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfStaffToDelete = Integer.parseInt(req.params("id")); //pull id - must match route segment
            Staff deleteStaff = Staff.findById(idOfStaffToDelete); //use it to find post
            deleteStaff.deleteStaff();
            return new ModelAndView(model, "success.hbs");
        }, new HandlebarsTemplateEngine());

        //get: show a form to update a staff
        get("/staff/update/:id", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            int idOfStaffToEdit = Integer.parseInt(req.params("id"));
            Staff editStaff = Staff.findById(idOfStaffToEdit);
            model.put("editStaff", editStaff);
            return new ModelAndView(model, "staff.hbs");
        }, new HandlebarsTemplateEngine());

    }
}


