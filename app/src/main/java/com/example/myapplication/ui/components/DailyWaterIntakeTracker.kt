package com.example.myapplication.ui.components


class DailyWaterIntakeTracker() {

    private var waterIntakeGoal:Int = 2500 /* in mili litres */
        get() {
            return field;
        }
        set(value: Int) {
            if(value < 0) {
                // throw exception
            }
            field = value;
        }
        fun increaseWaterIntakeGoal(incrementValue: Int): Unit {
            waterIntakeGoal = waterIntakeGoal + incrementValue;
        }
        fun decreaseWaterIntakeGoal(decrementValue: Int): Unit {
            if(waterIntakeGoal - decrementValue < 0) {
                // throw exception
            }
            waterIntakeGoal = waterIntakeGoal - decrementValue;
        }
    private var waterIntakeSoFar:Int = 0
        get() {
            return field
        }
        fun incrementWaterIntakeSoFar(waterIntake: Int): Unit {
            waterIntakeSoFar += waterIntake;
        }

        fun decrementWaterIntakeSoFar(decrementValue: Int): Unit {
            if(waterIntakeSoFar - decrementValue >= 0) {
                waterIntakeSoFar -= decrementValue;
            }
            else {
                // throw exception;
            }
        }

    var waterIntakeRecordsList: WaterIntakeRecordsList = WaterIntakeRecordsList()
    init {
        waterIntakeGoal = 2500;
    }
}