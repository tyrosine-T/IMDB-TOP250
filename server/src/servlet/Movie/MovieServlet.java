package servlet.Movie;
import dao.MovieDAO;
import dao.adaDAO;
import entity.Movie;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet({"/Movies/movies", "/Movies/movieselectById", "/Movies/movieupdate", "/Movies/moviefilter",
        "/Movies/movieadd", "/Movies/moviedelete", "/Movies/advancedsearch"})
public class MovieServlet extends HttpServlet {

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

        if (requestURI.endsWith("/Movies/movies")) {
            displayMovieList(request, response);
        } else if (requestURI.endsWith("/Movies/movieselectById")) {
            String movie_id = request.getParameter("movie_id");
            MovieDAO movieDAO = new MovieDAO();
            Movie movie = movieDAO.selectById(movie_id);
            request.setAttribute("user_attribute", movie);
            request.getRequestDispatcher("movieupdate.jsp").forward(request, response);
        } else if (requestURI.endsWith("/Movies/movieupdate")) {
            String movie_id = request.getParameter("movie_id");
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String image_url = request.getParameter("image_url");
            String content_rating = request.getParameter("content_rating");
            String duration = request.getParameter("duration");
            String release_year = request.getParameter("release_year");
            String director = request.getParameter("director");
            String actor = request.getParameter("actor");
            int rating = Integer.parseInt(content_rating);
            String genre = request.getParameter("genre");
            Movie movie = new Movie(movie_id, title, description, image_url,
                    content_rating, duration, release_year, director, actor, rating, genre);

            MovieDAO movieDAO = new MovieDAO();
            int count = movieDAO.update(movie);

            String str = (count > 0) ? "修改信息成功" : "修改信息失败";

            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('" + str + "');");
            out.print("location.href='movies'");
            out.print("</script>");
            out.close();
        } else if (requestURI.endsWith("/Movies/moviefilter")) {
            String searchKey = request.getParameter("searchKey");
            String searchValue = request.getParameter("searchValue");

            MovieDAO dao = new MovieDAO();
            List<Movie> filteredMovies = dao.filterMovies(searchKey, searchValue);

            request.setAttribute("FilteredMovies", filteredMovies);
            request.getRequestDispatcher("moviefilter.jsp").forward(request, response);
        } else if (requestURI.endsWith("/Movies/movieadd")) {
            addMovie(request, response);
        } else if (requestURI.endsWith("/Movies/moviedelete")) {
            String movie_id = request.getParameter("movie_id");
            MovieDAO movieDAO = new MovieDAO();
            System.out.println(movie_id);
            int count = movieDAO.delete(movie_id);

            String str = (count > 0) ? "删除成功" : "删除失败";

            PrintWriter out = response.getWriter();
            out.print("<script>");
            out.print("alert('" + str + "');");
            out.print("location.href='movies'");
            out.print("</script>");
            out.close();
        }else if (requestURI.endsWith("/Movies/advancedsearch")) {
            String title = request.getParameter("title");
            String director = request.getParameter("director");
            String actor = request.getParameter("actor");
            String genre = request.getParameter("genre");
            String content_rating = request.getParameter("content_rating");
            String duration = request.getParameter("duration");
            String release_year = request.getParameter("release_year");

            MovieDAO movieDAO = new MovieDAO();
            List<Movie> movies = adaDAO.searchMovies(title, director, actor, genre, content_rating, duration, release_year);
            request.setAttribute("FilteredMovies", movies);
            request.getRequestDispatcher("moviefilter.jsp").forward(request,response);
        }else {
            throw new IllegalArgumentException("URL不匹配！");
        }
    }

    private void displayMovieList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MovieDAO movieDAO = new MovieDAO();
        List<Movie> movieList = movieDAO.getAllMovies();
        request.setAttribute("MovieList", movieList);
        request.getRequestDispatcher("movies.jsp").forward(request, response);
    }

    private void addMovie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String movie_id = request.getParameter("movie_id");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String image_url = request.getParameter("image_url");
        String content_rating = request.getParameter("content_rating");
        String duration = request.getParameter("duration");
        String release_year = request.getParameter("release_year");

        String director_name = request.getParameter("director_name");
        String actor_name = request.getParameter("actor_name");
        String genre = request.getParameter("genre");
        //System.out.println(genre);
        int rating = Integer.parseInt(request.getParameter("rating"));
        int rating_count = Integer.parseInt(request.getParameter("rating_count"));
        Movie movie = new Movie(movie_id, title, description, image_url,
                content_rating, duration, release_year, director_name, actor_name, rating, genre);

        MovieDAO movieDAO = new MovieDAO();
        int count = movieDAO.addMovie(movie,rating_count);

        String str = (count > 0) ? "添加信息成功" : "添加信息失败";

        PrintWriter out = response.getWriter();
        out.print("<script>");
        out.print("alert('" + str + "');");
        out.print("location.href='movies'");
        out.print("</script>");
        out.close();
    }
}