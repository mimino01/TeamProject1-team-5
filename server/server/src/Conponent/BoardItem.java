package Conponent;

public class BoardItem {
    public String[] user;
    public String host;
    public Option userData;
    public Option hostData;
    public int numberOfUser;
    public String destination;
    public float departureTime;

    public BoardItem() {
    }

    public BoardItem(String host, String destination, float departureTime) {
        this.host = host;
        this.destination = destination;
        this.departureTime = departureTime;
    }

    public boolean addUser(String id) {
        if (numberOfUser > 4) {
            return false;
        } else {
            user[numberOfUser] = id;
            numberOfUser++;
            return true;
        }
    }

    public String[] getUser() {
        return user;
    }

    public void setUser(String[] user) {
        this.user = user;
    }
}
