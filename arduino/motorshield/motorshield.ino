void setup() {
  
  //Setup Channel A
  pinMode(12, OUTPUT); //Initiates Motor Channel A
  pinMode(9, OUTPUT); //Initiates Brake Channel A  


 //Setup Channel B
  pinMode(13, OUTPUT); //Initiates Motor Channel B
  pinMode(8, OUTPUT); //Initiates Brake Channel B 
}

void loop(){  
    
  //Drive motor A
  digitalWrite(12, HIGH); //High is forward direction, use low for backwards
  digitalWrite(9, LOW);   //Disengage the Brake
  analogWrite(3, 130);   //Spins the motor at full speed use different values between 0 and 255 for speed
  
  delay(2000);
  
  digitalWrite(9, HIGH); //Engage the Brake

  delay(2000);
  
  //Drive motor A backwards slowly
  digitalWrite(12, LOW); //Low is backwards direction, use High for forwards
  digitalWrite(9, LOW);   //Disengage the Brake
  analogWrite(3, 100);   //Spins the motor at low speed use different values between 0 and 255 for speed
  
  delay(3000);
  
  digitalWrite(9, HIGH); //Engage the Brake

  delay(2000);
  
}
