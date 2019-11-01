package com.geekq.learnguava.guava.concurrent;

public class TokenBucketExample
{

    public static void main(String[] args)
    {
        final TokenBucket tokenBucket = new TokenBucket();
        for (int i = 0; i < 200; i++)
        {
            new Thread(tokenBucket::buy).start();
        }
    }
}
