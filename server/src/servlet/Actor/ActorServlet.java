package servlet.Actor;
import dao.ActorDAO;
import entity.Actor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/Actors/actors", "/Actors/actorselectById", "/Actors/actorupdate", "/Actors/actorfilter", "/Actors/actoradd", "/Actors/actordelete"})
public class ActorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println("GET");
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("POST");
        String requestURI = request.getRequestURI();
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        if (requestURI.endsWith("/Actors/actors")) {
            displayActorList(request, response);
        } else if (requestURI.endsWith("/Actors/actorselectById")) {
            int actor_id = Integer.parseInt(request.getParameter("actor_id"));
            ActorDAO actorDAO = new ActorDAO();
            Actor actor = actorDAO.selectById(actor_id);
            request.setAttribute("user_attribute", actor);
            request.getRequestDispatcher("actorupdate.jsp").forward(request, response);
        } else if (requestURI.endsWith("/Actors/actorupdate")) {
            Integer actor_id = Integer.parseInt(request.getParameter("actor_id"));
            String movie_id = request.getParameter("movie_id");
            String name = request.getParameter("name");
            Actor actor = new Actor();
            actor.setActor_id(actor_id);
            actor.setMovie_id(movie_id);
            actor.setName(name);

            ActorDAO actorDAO = new ActorDAO();
            int count = actorDAO.update(actor);

            String str = (count > 0) ? "修改信息成功" : "修改信息失败";

            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('" + str + "');");
            out.print("location.href='actors'");
            out.print("</script>");
            out.close();
        } else if (requestURI.endsWith("/Actors/actorfilter")) {
            String searchKey = request.getParameter("searchKey");
            String searchValue = request.getParameter("searchValue");

            ActorDAO dao = new ActorDAO();
            List<Actor> filteredActors = dao.filterActors(searchKey, searchValue);

            request.setAttribute("FilteredActors", filteredActors);
            request.getRequestDispatcher("actorfilter.jsp").forward(request, response);
        } else if (requestURI.endsWith("/Actors/actoradd")) {
            addActor(request, response);
        } else if (requestURI.endsWith("/Actors/actordelete")) {
            int actor_id = Integer.parseInt(request.getParameter("actor_id"));
            ActorDAO actorDAO = new ActorDAO();
            //System.out.println(actor_id);
            int count = actorDAO.delete(actor_id);

            String str = (count > 0) ? "删除成功" : "删除失败";

            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('" + str + "');");
            out.print("location.href='actors'");
            out.print("</script>");
            out.close();
        } else {
            throw new IllegalArgumentException("URL不匹配！");
        }
    }

    private void displayActorList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ActorDAO actorDAO = new ActorDAO();
        List<Actor> actorList = actorDAO.getAllActors();
        request.setAttribute("ActorList", actorList);
        request.getRequestDispatcher("actors.jsp").forward(request, response);
    }

    private void addActor(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Integer actor_id = Integer.parseInt(request.getParameter("actor_id"));
        String movie_id = request.getParameter("movie_id");
        String name = request.getParameter("name");
        Actor actor = new Actor(movie_id, name);
        //System.out.println(actor.getName());
        ActorDAO actorDAO = new ActorDAO();
        int count = actorDAO.addActor(actor);

        String str = (count > 0) ? "添加信息成功" : "添加信息失败";

        PrintWriter out = response.getWriter();
        out.print("<script>");
        out.print("alert('" + str + "');");
        out.print("location.href='actors'");
        out.print("</script>");
        out.close();
    }
}