package com.darrenh.actor {

    import akka.actor._
    import akka.cluster._
    import akka.cluster.ClusterEvent._
    import scala.collection.mutable

    class MonitorActor extends Actor with ActorLogging {
        val cluster = Cluster(context.system)

        // subscribe to cluster changes, re-subscribe when restart 
        override def preStart(): Unit = {
            log debug "Starting ClusterMonitorActor"
            cluster.subscribe(self, InitialStateAsEvents, classOf[ClusterDomainEvent])
        }

        // clean up on shutdown
        override def postStop(): Unit = cluster unsubscribe self

        // handle the member events
        var memberNodes = mutable.HashSet.empty[Address]

        override def receive = {
            case MemberJoined(m) =>
            log.info("Member Joining: {}", m.address)
            memberNodes += m.address

            case MemberWeaklyUp(m) =>
            log.info("Member Weakly Up: {}", m.address)
            memberNodes += m.address

            case MemberUp(m) =>
            log.info("Member Up: {}", m.address)
            memberNodes += m.address

            case MemberRemoved(m, s) =>
            log.info("Member Removed: {}, Previous Status: {}", m.address, s)
            memberNodes -= m.address

            case MemberLeft(m) =>
            log.info("Member Leaving: {}", m.address)
            memberNodes -= m.address

            case MemberExited(m) =>
            log.info("Member Exited: {}", m.address)
            memberNodes -= m.address

            case LeaderChanged(l) =>
            log.info("Leader Changed: {}", l.getOrElse("none"))

            case RoleLeaderChanged(role, l) =>
            log.info("Role Leader Changed: {}, Role: {}", l.getOrElse("none"), role)

            case ClusterShuttingDown =>
            log.info("Cluster Shutting Down")

            case UnreachableMember(m) =>
            log.warning("Member Unreachable: {}", m.address)

            case ReachableMember(m) =>
            log.info("Member Reachable: {}", m.address)
        }
    }
}