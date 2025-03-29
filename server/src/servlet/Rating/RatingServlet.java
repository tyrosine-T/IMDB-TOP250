package servlet.Rating;
import dao.RatingDAO;
import entity.Rating;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/Ratings/ratings", "/Ratings/ratingselectById", "/Ratings/ratingupdate", "/Ratings/ratingfilter", "/Ratings/ratingadd", "/Ratings/ratingdelete"})
public class RatingServlet extends HttpServlet {

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

        if (requestURI.endsWith("/Ratings/ratings")) {
            displayRatingList(request, response);
        } else if (requestURI.endsWith("/Ratings/ratingselectById")) {
            int rating_id = Integer.parseInt(request.getParameter("rating_id"));
            RatingDAO ratingDAO = new RatingDAO();
            Rating rating = ratingDAO.selectById(rating_id);
            request.setAttribute("user_attribute", rating);
            request.getRequestDispatcher("ratingupdate.jsp").forward(request, response);
        } else if (requestURI.endsWith("/Ratings/ratingupdate")) {
            Integer rating_id = Integer.parseInt(request.getParameter("rating_id"));
            String movie_id = request.getParameter("movie_id");
            int rating = Integer.parseInt(request.getParameter("rating"));
            Integer rating_count = Integer.parseInt(request.getParameter("rating_count"));

            Rating u = new Rating();
            u.setRating_id(rating_id);
            u.setMovie_id(movie_id);
            u.setRating(rating);
            u.setRating_count(rating_count);

            RatingDAO ratingDAO = new RatingDAO();
            int count = ratingDAO.update(u);

            String str = (count > 0) ? "修改信息成功" : "修改信息失败";

            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('" + str + "');");
            out.print("location.href='ratings'");
            out.print("</script>");
            out.close();
        } else if (requestURI.endsWith("/Ratings/ratingfilter")) {
            String searchKey = request.getParameter("searchKey");
            String searchValue = request.getParameter("searchValue");

            RatingDAO dao = new RatingDAO();
            List<Rating> filteredRatings = dao.filterRatings(searchKey, searchValue);

            request.setAttribute("FilteredRatings", filteredRatings);
            request.getRequestDispatcher("ratingfilter.jsp").forward(request, response);
        } else if (requestURI.endsWith("/Ratings/ratingadd")) {
            addRating(request, response);
        } else if (requestURI.endsWith("/Ratings/ratingdelete")) {
            int rating_id = Integer.parseInt(request.getParameter("rating_id"));
            RatingDAO ratingDAO = new RatingDAO();
            //System.out.println(rating_id);
            int count = ratingDAO.delete(rating_id);

            String str = (count > 0) ? "删除成功" : "删除失败";

            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('" + str + "');");
            out.print("location.href='ratings'");
            out.print("</script>");
            out.close();
        } else {
            throw new IllegalArgumentException("URL不匹配！");
        }
    }

    private void displayRatingList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RatingDAO ratingDAO = new RatingDAO();
        List<Rating> ratingList = ratingDAO.getAllRatings();
        request.setAttribute("RatingList", ratingList);
        request.getRequestDispatcher("ratings.jsp").forward(request, response);
    }

    private void addRating(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Integer rating_id = Integer.parseInt(request.getParameter("rating_id"));
        String movie_id = request.getParameter("movie_id");
        int rating = Integer.parseInt(request.getParameter("rating"));
        RatingDAO ratingDAO = new RatingDAO();

        int count = ratingDAO.addRating(movie_id, rating);

        String str = (count > 0) ? "添加信息成功" : "添加信息失败";

        PrintWriter out = response.getWriter();
        out.print("<script>");
        out.print("alert('" + str + "');");
        out.print("location.href='ratings'");
        out.print("</script>");
        out.close();
    }
}