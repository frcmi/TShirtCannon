package first.robot.mechanisms;

import static org.wpilib.units.Units.Seconds;

import org.wpilib.command3.Command;
import org.wpilib.command3.Coroutine;
import org.wpilib.command3.Mechanism;
import org.wpilib.hardware.bus.CANPort;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

public class CannonMechanism implements Mechanism {
    private final SparkMax cannon1 = new SparkMax(CANPort.CAN_D0, 4, MotorType.kBrushless);
    private final SparkMax cannon2 = new SparkMax(CANPort.CAN_D0, 5, MotorType.kBrushless);
    private final SparkMax cannon3 = new SparkMax(CANPort.CAN_D0, 6, MotorType.kBrushless);
    private final SparkMax cannon4 = new SparkMax(CANPort.CAN_D0, 7, MotorType.kBrushless);

    public CannonMechanism() {

    }

    public void periodic() {

    }

    public Command shoot1() {
        return run(Coroutine -> {
            cannon1.setVoltage(12);
            Coroutine.wait(Seconds.of(0.3));
            cannon1.setVoltage(0);
        }).named("shoot1");
    }

    public Command shoot2() {
        return run(Coroutine -> {
            cannon2.setVoltage(12);
            Coroutine.wait(Seconds.of(0.3));
            cannon2.setVoltage(0);
        }).named("shoot2");
    }

    public Command shoot3() {
        return run(Coroutine -> {
            cannon3.setVoltage(12);
            Coroutine.wait(Seconds.of(0.3));
            cannon3.setVoltage(0);
        }).named("shoot3");
    }

    public Command shoot4() {
        return run(Coroutine -> {
            cannon4.setVoltage(12);
            Coroutine.wait(Seconds.of(0.3));
            cannon4.setVoltage(0);
        }).named("shoot4");
    }
}