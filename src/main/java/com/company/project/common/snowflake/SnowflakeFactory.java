package com.company.project.common.snowflake;

/**
 * @author xuzefan  2019/7/12 15:15
 */
public class SnowflakeFactory {

    private static SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(1,1);

    public static SnowflakeIdWorker getSnowflakeIdWorker() {
        return snowflakeIdWorker;
    }

    public static SnowflakeIdWorker getNewSnowflakeIdWorker(long workerId, long datacenterId) {
        return new SnowflakeIdWorker(workerId,datacenterId);
    }
}
