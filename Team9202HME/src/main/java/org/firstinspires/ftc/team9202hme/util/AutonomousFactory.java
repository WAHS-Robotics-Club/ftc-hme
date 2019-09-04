package org.firstinspires.ftc.team9202hme.util;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcontroller.internal.FtcOpModeRegister;
import org.firstinspires.ftc.robotcontroller.internal.OpModeFactory;
import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;
import org.firstinspires.ftc.team9202hme.program.AutonomousProgram;
import org.firstinspires.ftc.team9202hme.program.MainAutonomousProgram;

import java.util.HashMap;
import java.util.Map;

public class AutonomousFactory implements OpModeFactory {
    static {
        FtcOpModeRegister.registerOpModeFactory(new AutonomousFactory());
    }
            
    @Override
    public Map<OpMode, OpModeMeta> generateOpModes() {
        Map<OpMode, OpModeMeta> opModes = new HashMap<>();

        AutonomousProgram.FieldPosition[] fieldPositions = {
                AutonomousProgram.FieldPosition.CRATER_FACING,
                AutonomousProgram.FieldPosition.DEPOT_FACING
        };

        MainAutonomousProgram.AdditionalSteps[] additionalSteps = {
                MainAutonomousProgram.AdditionalSteps.NONE,
                MainAutonomousProgram.AdditionalSteps.CLAIM_ONLY,
                MainAutonomousProgram.AdditionalSteps.PARK_ONLY,
                MainAutonomousProgram.AdditionalSteps.CLAIM_AND_PARK
        };

        for(final AutonomousProgram.FieldPosition fieldPosition : fieldPositions) {
            for(final MainAutonomousProgram.AdditionalSteps additionalStep : additionalSteps) {
                opModes.put(new LinearOpMode() {
                    private AutonomousProgram program = new MainAutonomousProgram(this, fieldPosition, additionalStep);

                    @Override
                    public void runOpMode() throws InterruptedException {
                        program.run();
                    }

                }, new OpModeMeta(fieldPosition + " (" + additionalStep + ")",
                        OpModeMeta.Flavor.AUTONOMOUS, "HME"));
            }
        }

        return opModes;
    }
}
