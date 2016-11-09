int dfi = 7;		// Input pin for forward driving
int dbi = 6;		// Input pin for reverse driving
int tri = 4;		// Input pin for turning right
int tli = 5;		// Input pin for turning left

int driveMotor = 12;   	// Driving pin for drive motor 
int driveBrake = 9;	// Braking pin for drive motor
int turnMotor = 13;  	// Driving pin for turning motor
int turnBrake = 8;    	// Braking pin for turning motor

int driveAnalChann = 3;  // Analog speed channel for driving motor
int turnAnalChann = 11;  // Analog speed channel for turning motor

void setup() {
  
  // initialise input pins
  pinMode(dfi, INPUT);
  pinMode(dbi, INPUT);
  pinMode(tri, INPUT);
  pinMode(tli, INPUT);
  
  // initialise output pins
  pinMode(driveMotor, OUTPUT);
  pinMode(driveBrake, OUTPUT);
  
  pinMode(turnMotor, OUTPUT);
  pinMode(turnBrake, OUTPUT);
  
  pinMode(driveAnalChann, OUTPUT);
  pinMode(turnAnalChann, OUTPUT);
  
}

void loop() {
  
   /*
    * The following nested if statements drive the motors.
    * When the correct pin is HIGH, a direction is given, the brake disengaged and the speed set.
    * Conversely, when the pin is LOW the brake is engaged.
    * Motors take values of HIGH or LOW to receive positive or negative voltage.
    */
   if (digitalRead(dfi) == HIGH) {
     
     digitalWrite(driveMotor, LOW);
     digitalWrite(driveBrake, LOW);
     analogWrite(driveAnalChann, 255);
     
   } else if (digitalRead(dfi) == LOW){
     digitalWrite(driveBrake, HIGH);   
   } 
   
   if (digitalRead(dbi) == HIGH) {
     
     digitalWrite(driveMotor, HIGH);
     digitalWrite(driveBrake, LOW);
     analogWrite(driveAnalChann, 255);
     
   } else if (digitalRead(dbi) == LOW){
     digitalWrite(driveBrake, HIGH);    
   } 
   
   if (digitalRead(tri) == HIGH) {
     
     digitalWrite(turnMotor, HIGH);
     digitalWrite(turnBrake, LOW);
     analogWrite(turnAnalChann, 255);
     
   } else if (digitalRead(tri) == LOW){
     digitalWrite(turnBrake, HIGH);   
   } 
   
   if (digitalRead(tli) == HIGH) {
     
     digitalWrite(turnMotor, LOW);
     digitalWrite(turnBrake, LOW);
     analogWrite(turnAnalChann, 255);
     
   } else if (digitalRead(tli) == LOW){
     digitalWrite(turnBrake, HIGH); 
  }

}
