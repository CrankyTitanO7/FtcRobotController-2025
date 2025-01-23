# 13965 log 

## log 3 - date - name

### to do

### what's new

### fun fact for today

## log 2 - 1/22/25 - big jaden

### to do
 - upload this log to github
 - 
 - test the teleop thing to make sure the motors are all facing the same direction (it's ok if no, but we need to incorporate into code)
 - add encoder wires to drivetrain 
 - figure out the COUNTS PER MOTOR REV (go to motor vendor website 
 - Measure wheel diameter in inches. this means 2x the distance from the center of the wheel to the average point of contact the wheel has with the ground. 
 - you may notice the variable in BaseAutoDist DRIVE_GEAR_REDUCTION. this will remain 1.0, because the motor gear is on a 1:1 ratio with the wheel's gear.


### what's new
- this log! it's cool and fun.

### fun fact for today
I guess I'm mostly adding this so other people can learn off of my mistakes.
Last year lots of seniors graduated, and now I'm on coding so I had to relearn lots of stuff. Here's a log so that doesn't happen again.

anyway so here's a couple of things future/current people should probably know:

#### installing android studio and jvm properly - versions after Ladybug
* install android studio. (look up android studio and press download and then install)
* install JVM 17 (any version) 
* clone repository from github

#### running opmodes on the driver station and hub
* do the above steps
* plug the driver station / android phone into a USB port on your computer. Do not use a USB-C PLUG! it doesn't work. you need a USB bus adapter, at least, for Mac.
* select the device in the device dropdown menu
* press play

* then once the thing loads, turn on robot
* connect phone / driver station to robot's wifi. ours is currently smth like "ROBOGOATS" / "password"
* initialize opmode and etc.

## log 1 - 1/22/25 - big jaden

well hello. I'm jaden. I am a coder on team 13965.

here are some logs that happened over the last few days. According to git history: 

### creation 1/21/25
 - forked the github, and learned how to make android studio ladybug work with old gradle / JVM 17.0
### moving in 1/22/25
 - deleted all the old files. 
 - added fresh teleop/auto files

this brings me to the latest commit message. here it is:

### added an auto file that activates the motors based on DISTANCE, not just time

#### to do:
- --> test the teleop thing to make sure the motors are all facing the same direction (it's ok if no, but we need to incorporate into code)
- --> add encoder wires to drivetrain
- --> figure out the COUNTS PER MOTOR REV (go to motor vendor website
- --> Measure wheel diameter in inches. this means 2x the distance from the center of the wheel to the average point of contact the wheel has with the ground.
- --> you may notice the variable in BaseAutoDist DRIVE_GEAR_REDUCTION. this will remain 1.0, because the motor gear is on a 1:1 ratio with the wheel's gear.

and now this next latest commit will add this log. I guess it's cool to think that other people will read these words. 

ok goodnight