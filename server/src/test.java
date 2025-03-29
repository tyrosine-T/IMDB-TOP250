
import java.util.List;
import dao.ActorDAO;
import entity.Actor;


public class test {
    public static void main(String[] args) {
        ActorDAO actorDAO = new ActorDAO();
        List<Actor> actorList = actorDAO.getAllActors();

        // 直接遍历并打印 des 列表中的每个元素
        for (Actor actor : actorList) {
            System.out.println(actor.getName());  // 调用 Department 类的 toString 方法
        }
    }
}