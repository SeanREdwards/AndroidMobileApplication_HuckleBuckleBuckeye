package com.example.hucklebucklebuckeye.model;

public class LogDBSchema {
    public static final class LogTable{
        public static final String NAME = "logs";

        public static final class Cols{
            public static final String UUID = "uuid";
            public static final String ACID = "acid";
            public static final String DATE = "date";
            public static final String STEPS = "steps";
            public static final String MAP = "map";
            public static final String DISTANCE = "distance";
            public static final String TIME = "time";
            public static final String COMPLETED = "completed";
        }
    }
}
