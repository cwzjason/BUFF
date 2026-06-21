package text;

/**
 * 用户会话管理器 — 单例模式
 * 在登录成功后设置当前用户，供全局访问
 */
public class Session {

    private static Session instance;
    private User currentUser;
    private int orderSequence = 0; // 当前会话内的订单序号，从1开始

    private Session() {}

    public static synchronized Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    /** 登录成功后调用 */
    public void login(User user) {
        this.currentUser = user;
        this.orderSequence = 0; // 每次登录重置序号
    }

    /** 退出登录时调用 */
    public void logout() {
        this.currentUser = null;
        this.orderSequence = 0;
    }

    /** 获取并递增当前会话的订单序号 */
    public int nextOrderSequence() {
        return ++this.orderSequence;
    }

    /** 获取当前订单序号（不递增） */
    public int getOrderSequence() {
        return this.orderSequence;
    }

    /** 获取当前登录用户 */
    public User getCurrentUser() {
        return currentUser;
    }

    /** 获取当前用户ID */
    public int getUserId() {
        if (currentUser == null) {
            throw new IllegalStateException("用户未登录，无法获取用户ID");
        }
        return currentUser.getId();
    }

    /** 获取当前用户名 */
    public String getUsername() {
        if (currentUser == null) {
            throw new IllegalStateException("用户未登录，无法获取用户名");
        }
        return currentUser.getName();
    }

    /** 是否已登录 */
    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
