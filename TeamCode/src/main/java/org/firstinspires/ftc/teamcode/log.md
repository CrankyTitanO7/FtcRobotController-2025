# 13965 log 

## log 7

### important debug side note: LEFT IS REFERENCED FIRST

### to do
- program automatic cycling system
- test auto
- program controller rumble
- and need to add encoder wire to arm at front

### just now I did
- make a funny auto, which only uses the front 2 encoders 
  - (even though its only strafing, if we want to turn later (highly unlikely) we can). 
- Should improve efficiency, 
- and also back left encoder just aint working. 
- time to see how far this encoder null pointer exception error keeps coming from

### stuff i did

- did a bunch at competition. 
  -  adjusted sensitivity -_______- apparently our robot is too sensitive.
  - added servos
  - inverted some controls
  - switched some servos from 

## log 6 - 1/24/25 - JADEN
- damn, i've been outvoted. the robot's name is Joshua? I think?

## log 5 - 1/24/25 - JADEN

## did
- counts per motor rev is 384.5 * 4 for gobilda 435s. apparently ppr is  1/4th cpr
- 1.75 is the radius of the wheel in inches
- added things to config
- testing teleop and auto
  - auto returns null pointer expression - i have no idea why
  - teleop turns off when not r trig: inversed it see if that holds
- the values are fine.
- added encoder wires to drivetrain

## to do
- not much really. sort of hope and pray.

## log 4 - 1/23/25 - JADEN

### what's new and fun fact

... damn, I code really well and fast at 12:09 AM.

### things I got done
- wrote teleop code for 2 drivers
- wrote auto code (basically just park)
  - made 2 new strafe methods and used that
- added servos to the code. Unfortunately I have no idea if what I put is accurate or not. One can only hope.
  -  servos and motors I added:
    -  motor arm
    -  arm's claw ( called claw 2) 
    -  motor linear slide 
    -  linear slide's claw ( called claw 1)
    -  wrist joint at top of linear slide
  all of this will be controlled by player 2 while player 1 drives.
- added fake values.
- programmed auto (just parking for now)

### things I should really get done one of these days
- test the following placeholder values:
  -  in teleop: 
    - val // controls linear slide speed
    - val3  // controls theoretically speed of wrist joint
    - armspeed // controls speed of arm
    - the two servos: currently set to max and min. probably not desireable.
  - in autoByDistance
    - the estimated turn distance in spinleft and spinright and etc.
    - figure out the COUNTS PER MOTOR REV (go to motor vendor website)
    - Measure wheel diameter in inches. this means 2x the distance from the center of the wheel to the average point of contact the wheel has with the ground.

- upload this part of the log to the engineering notebook
- add encoder wires to drivetrain



## log 3 - 1/23/25 - JADEN 

### things i got done

- tested teleop, discussed auto plan and etc.

### to do

- add servos (or at least, a quick servo function)
- program auto (just parking for now)
- add dual controller support in teleop
- upload this part of the log to the engineering notebook
- figure out the COUNTS PER MOTOR REV (go to motor vendor website
- Measure wheel diameter in inches. this means 2x the distance from the center of the wheel to the average point of contact the wheel has with the ground. 
- add encoder wires to drivetrain
- maybe tone down the speed a little... she drives a little aggressive.
- name the robot? I was thinking Birdie, because she flies across the field.


### what's new

IT WORKED!!!

today the robot moved. 

Finally, I feel like I know what I'm doing. 

the sample teleop testing mode worked!

### fun fact for today

- when programming, plug the computer into the control hub -_____- which was really obvious in retrospect
- also, on Macs, one must use a USB-C to USB-A adapter. Otherwise, it won't recognize on Android Studio (eg the USB-C to USB-C charging cable will not work.)



## log 2 - 1/22/25 - JADEN

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




## log 1 - 1/22/25 - JADEN

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