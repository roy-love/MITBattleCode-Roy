package Robot;
import battlecode.common.*;

public class Archon extends Robot {
    @Override
    public void onUpdate() {
        int numberOfGardeners =0;
        while (true) {
            try{
                Direction direction = randomDirection();
                if (robotController.canHireGardener(direction) && Math.random() < .01 && numberOfGardeners < 16) {
                    robotController.hireGardener(direction);
                    System.out.println("number of gardeners " + numberOfGardeners);
                    numberOfGardeners ++;
                }

                if (robotController.getTeamBullets() > 1000) {
                    robotController.donate(robotController.getTeamBullets() - 700);
                }
                tryMove(direction);
                Clock.yield();
            } catch (Exception e) {
                System.out.println("Exception in Archon");
                e.printStackTrace();
            }
        }
    }
}
