package org.firstinspires.ftc.robotcontroller.internal;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.internal.opmode.OpModeMeta;

import java.util.Map;

public interface OpModeFactory {
    Map<OpMode, OpModeMeta> generateOpModes();
}
