package Conponent;

public class BoardItem {
    final int MAXUSER = 4;
    public String[] user = new String[MAXUSER];
    public String host;
    public int numberOfUser;
    public String destination;
    public float departureTime;
    public Double[] coordinate = new Double[2];

    //초기화
    public BoardItem() {
    }

    //방장 아이디, 방장 위치, 목적지, 출발시각 입력받음
    public BoardItem(String host, String destination, float departureTime, Double Latitude, Double longitude) {
        this.host = host;
        this.destination = destination;
        this.departureTime = departureTime;
        this.coordinate[0] = Latitude;
        this.coordinate[1] = longitude;
    }

    //유저 추가
    public boolean addUser(String id) {
        if (numberOfUser > 4) {
            return false;
        } else {
            user[numberOfUser] = id;
            numberOfUser++;
            return true;
        }
    }

    //유저맴버 정보 반환
    public String[] getUser() {
        return user;
    }

    public String[][] getBoard() {
        String[][] req = new String[10][MAXUSER];

        req[0][0] = this.host;
        req[1] = this.user;
        req[2][0] = Double.toString(this.coordinate[0]);
        req[2][1] = Double.toString(this.coordinate[1]);
        req[3][0] = destination;
        req[4][0] = Float.toString(departureTime);

        return req;
    }
}
