// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.function.Supplier;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class Robot extends TimedRobot {
	CommandXboxController driver = new CommandXboxController(0);

	SparkMax leftLeader = new SparkMax(10, MotorType.kBrushless);
	SparkMax leftFollower = new SparkMax(11, MotorType.kBrushless);
	SparkMax rightLeader = new SparkMax(20, MotorType.kBrushless);
	SparkMax rightFollower = new SparkMax(21, MotorType.kBrushless);

	Supplier<Boolean> slowBind = ()-> driver.getLeftTriggerAxis() >= .5;
	
	public Robot() {
		SparkMaxConfig leftcfg = new SparkMaxConfig();
		leftcfg.inverted(true);
		leftcfg.idleMode(IdleMode.kBrake);
		leftLeader.configure(leftcfg, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

		SparkMaxConfig rightcfg = new SparkMaxConfig();
		rightcfg.idleMode(IdleMode.kBrake);
		rightLeader.configure(rightcfg, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
		
		SparkMaxConfig leftFollowerCfg = new SparkMaxConfig();
		leftFollowerCfg.follow(leftLeader.getDeviceId());
		leftFollowerCfg.idleMode(IdleMode.kBrake);
		leftFollower.configure(leftFollowerCfg, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
		
		SparkMaxConfig rightFollowerCfg = new SparkMaxConfig();
		rightFollowerCfg.follow(rightLeader.getDeviceId());
		rightFollowerCfg.idleMode(IdleMode.kBrake);
		rightFollower.configure(rightFollowerCfg, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
	}
	
	@Override
	public void robotPeriodic() {}
	
	@Override
	public void autonomousInit() {}
	
	@Override
	public void autonomousPeriodic() {}
	
	@Override
	public void teleopInit() {}
	
	@Override
	public void teleopPeriodic() {
		double drive = driver.getLeftY();
		drive /= slowBind.get() ? 2 : 1;
		double steer = driver.getRightX();
		
		leftLeader.set(drive-steer);
		rightLeader.set(drive+steer);
	}
	
	@Override
	public void disabledInit() {}
	
	@Override
	public void disabledPeriodic() {}
	
	@Override
	public void testInit() {}
}