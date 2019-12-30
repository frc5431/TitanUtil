package frc.team5431.titan.core.hololens;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Hololens {

    private NetworkTable m_Table;
    private String m_Message;
    private Lock m_Mutex;

    public Hololens() {
        m_Table = NetworkTableInstance.getDefault().getTable("hololens");
        m_Message = new String();
        m_Mutex = new ReentrantLock(true);
    }

    public void setCustomMessage(final String msg) {
        m_Mutex.lock();
        m_Message = String.valueOf(msg);
        m_Mutex.unlock();
    }

    public String getCustomMessage() {
        String buffer;

        m_Mutex.lock();
        buffer = String.valueOf(m_Message);
        m_Mutex.unlock();

        return buffer;
    }

    public void tick() {

        DriverStation driverStation = DriverStation.getInstance();
        double time  = driverStation.getMatchTime();
        int matchNumber = driverStation.getMatchNumber();

        m_Table.getEntry("time").setDouble(time);
        m_Table.getEntry("match_number").setDouble(matchNumber);
        m_Table.getEntry("custom").setString(getCustomMessage());
    }
}