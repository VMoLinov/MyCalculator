package molinov.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Fields implements Parcelable {

    String main;
    String slave;
    ArrayList<String> history;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getSlave() {
        return slave;
    }

    public void setSlave(String slave) {
        this.slave = slave;
    }


    public String getHistory() {
        StringBuilder a = new StringBuilder();
        for (int i = 0; i < history.size(); i++) {
            a.append(history.get(i));
            a.append(System.lineSeparator());
        }
        return a.toString();
    }

    public void addHistory(String s) {
        history.add(s);
    }

    public static Creator<Fields> getCREATOR() {
        return CREATOR;
    }

    protected Fields() {
        main = "";
        slave = "";
        history = new ArrayList<>();
    }

    protected Fields(Parcel in) {
//        main = in.readString();
//        slave = in.readString();
//        history = in.readArrayList(null);
    }

    public static final Creator<Fields> CREATOR = new Creator<Fields>() {
        @Override
        public Fields createFromParcel(Parcel in) {
            return new Fields(in);
        }

        @Override
        public Fields[] newArray(int size) {
            return new Fields[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(main);
//        dest.writeString(slave);
//        dest.writeList(history);
    }
}
