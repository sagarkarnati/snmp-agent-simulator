#!/bin/sh

PRJ_HOME=`pwd`
PRJ_JAVA_HOME=$PRJ_HOME/jre
PRJ_LIB=$PRJ_HOME/lib
PRJ_CONF=$PRJ_HOME/conf
PRJ_MIBS=$PRJ_HOME/mibs
PRJ_LOGS=$PRJ_HOME/logs
PRJ_MODELS=$PRJ_HOME/models
PATH=$JAVA_HOME/bin:$PATH
export PATH PRJ_HOME PRJ_LIB

JAVA_CLASSPATH=$PRJ_HOME:$PRJ_HOME/*.*:$PRJ_CONF/*.*:$PRJ_MIBS/*.*:$PRJ_MODELS/*.*:$PRJ_LOGS/*.*:$PRJ_LIB/ant-antlr-1.6.3.jar:$PRJ_LIB/antlr-2.7.5H3.jar:$PRJ_LIB/asm-attrs.jar:$PRJ_LIB/asm.jar:$PRJ_LIB/castor-1.1.1.jar:$PRJ_LIB/cglib-2.1.jar:$PRJ_LIB/cglib-nodep-2.1_3.jar:$PRJ_LIB/commons-collections-2.1.1.jar:$PRJ_LIB/commons-dbcp.jar:$PRJ_LIB/commons-lang-2.5.jar:$PRJ_LIB/commons-logging-1.0.3.jar:$PRJ_LIB/commons-logging-1.0.4.jar:$PRJ_LIB/commons-pool.jar:$PRJ_LIB/dom4j-1.6.jar:$PRJ_LIB/ehcache-1.1.jar:$PRJ_LIB/grammatica-1.5.jar:$PRJ_LIB/hibernate3-3.2.3.GA.jar:$PRJ_LIB/hsqldb.jar:$PRJ_LIB/javassist-3.7.ga.jar:$PRJ_LIB/jcl-over-slf4j-1.5.11.jar:$PRJ_LIB/jersey-client-1.4.jar:$PRJ_LIB/jersey-core-1.4.jar:$PRJ_LIB/jta.jar:$PRJ_LIB/jzlib-1.0.7.jar:$PRJ_LIB/log4j-1.2.15.jar:$PRJ_LIB/log4j-1.2.9.jar:$PRJ_LIB/mibble-mibs-2.9.2.jar:$PRJ_LIB/mibble-parser-2.9.2.jar:$PRJ_LIB/mina-core-2.0.1.jar:$PRJ_LIB/mina-example-2.0.1.jar:$PRJ_LIB/mina-filter-compression-2.0.1.jar:$PRJ_LIB/mina-integration-beans-2.0.1.jar:$PRJ_LIB/mina-integration-jmx-2.0.1.jar:$PRJ_LIB/mina-integration-ognl-2.0.1.jar:$PRJ_LIB/mina-integration-xbean-2.0.1.jar:$PRJ_LIB/mina-statemachine-2.0.1.jar:$PRJ_LIB/mina-transport-apr-2.0.1.jar:$PRJ_LIB/netty-3.2.0.BETA1.jar:$PRJ_LIB/ognl-2.7.3.jar:$PRJ_LIB/slf4j-api-1.5.0.jar:$PRJ_LIB/slf4j-log4j12-1.5.0.jar:$PRJ_LIB/SNMP4J.jar:$PRJ_LIB/spring-2.5.6.jar:$PRJ_LIB/tomcat-apr-5.5.23.jar:$PRJ_LIB/xbean-spring-3.6.jar:$PRJ_LIB/xerces-2.6.2.jar:$PRJ_LIB/AgentSimulator.jar:

#java calls main class
echo $JAVA_CLASSPATH
$PRJ_JAVA_HOME/bin/java -classpath $JAVA_CLASSPATH com.mindtree.agentsim.main.AgentSimMain
