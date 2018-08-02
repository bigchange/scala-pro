#!/bin/bash
spark_dir=/data/home/mindcube/youcj/tools/spark-1.6.0-bin-hadoop2.6
app_dir=/data/home/mindcube/youcj/app
jarName=resume-1.0-SNAPSHOT.jar
$spark_dir/bin/spark-submit \
--master local \
--name CountTest \
--class com.higgs.usercase.job_normal.App \
--executor-memory 3G \
--total-executor-cores 2  $app_dir/jar/$jarName \
file:///data/home/mindcube/youcj/shell/data/job_dict_des/all
#hdfs://172.20.0.104:8022/user/mindcube/test_out/candidate_company_names/20180702/all/uidmap