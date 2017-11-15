package Robot;
import battlecode.common.*;

public class Gardener extends Robot {
    public void onUpdate() {
        boolean settled = false;
        Direction gardnerDir = null;
        int numberOfSoldiers = 0;

        while (true) {
            try {
                Direction direction = randomDirection();
                if(gardnerDir == null) {
                    gardnerDir = direction;
                }
                if (!settled){
                    if(robotController.getTeamBullets() > 250 && numberOfSoldiers < 10) {
                        System.out.println("number of soldiers " + numberOfSoldiers);
                        if(robotController.canBuildRobot(RobotType.SOLDIER, direction)){
                            robotController.buildRobot(RobotType.SOLDIER, direction);
                            numberOfSoldiers ++;
                        }
                    }
                }
                if (!(robotController.isCircleOccupiedExceptByThisRobot(robotController.getLocation(), robotType.bodyRadius * 4.0f))&& !settled) {
                    settled = true;
                    if(robotController.canPlantTree(gardnerDir)) {
                        robotController.plantTree(gardnerDir);
                    }
                }
                if (settled) {
                    if(robotController.canPlantTree(direction)){
                        robotController.plantTree(direction);
                    }
                }
                TreeInfo[] trees = robotController.senseNearbyTrees(robotType.bodyRadius * 2, myTeam);
                TreeInfo minHealthTree = null;
                for (TreeInfo tree : trees) {
                    if (tree.health < 70){
                        if(minHealthTree == null || tree.health < minHealthTree.health) {
                            minHealthTree = tree;
                        }
                    }
                }
                if (minHealthTree != null) {
                    robotController.water(minHealthTree.ID);
                }
                if(!settled) {
                    if(tryMove(gardnerDir)){
                        System.out.println("Moved");
                    }else {
                        gardnerDir = randomDirection();
                        tryMove(gardnerDir);
                    }
                }
                Clock.yield();
            } catch (Exception e) {
                System.out.println("This Gardner Exception");
                e.printStackTrace();
            }
        }
    }
}
