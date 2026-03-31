# Gyroscope-Controlled Ball Game

# 1. What the App Does
A Simple Maze game, where the user can tilt the ball around the screen, avoiding the obstacles in center of the screen.


# 2. Screenshot and Video of the Running App

<img width="400" height="750" alt="Screenshot_20260331_180103" src="https://github.com/user-attachments/assets/64e7e717-0e87-420f-9606-342accb5cdf6" />






# 3. Device / Emulator / SDK Version

- **Emulator Device:** Medium Phone API 36.1 (Android 16.0 ("Baklava"))      
- **Minimum SDK:** 26  
- **Target SDK:** 36


# 4. AI Disclosure
Utilized AI (ChatGPT) to aid in my collision logic, which was basically how the movement of the ball was restricted based on the placement of the obstacles.
Also Used AI to aid in how I updated my ball cordinates, I had issues with my ball not moving properly but ChatGPT helped me realized that I had to multiply the values
I got from the sensor by a much bigger value to see my movements better and faster. It also helped me realize that I needed to clamp my ball as well to prevent the 
ball from disappearing. 
