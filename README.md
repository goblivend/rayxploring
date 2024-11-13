# rayxploring
Repo where I'll try stuff arround ray casting, tracing, marching .... whithout looking at how they work, (might after a first version though)

## Thoughts

First when I thought about raytracing (and all) I saw it the physical way : rays coming from a light source and moving at a constant speed. Which is from what I understand raycasting
It seems really slow and I'm not sure about checking if you intersect nor the steps to take in order to avoid skipping an object

### First thought

An idea to optimise would have beed to create an oversized margin box in which more precise movements would be needed and outside wider movements would be ok

### Second thoughts

Then another idea that would clearly be faster would be to calculate an intersection point between an object and a ray but then I will need maths formulas for the objects.

So first, for starter only a light source => set of rays arranged like a cone, a plane as the simplest object and a screen that would be the camera. The difficulty would be how to get the pixels :
    
- Have the position of intersection between the ray and the screen as the position but then a sphere would theoretically just fill the screen instead of beeing restricted to the circle

- Another idea would be to have 2 kinds of "lights". 1 that sends the rays and sets the color of the place, another that sends rays but instead gets the colors set previously. The only drawback would be for miror look, a camera change should change the pixel but here it wouldn't
  - Instead of having a rectangle as the camera, having a "cone" with a rectangular eye => vector for general direction, 2 secondary vectors for up/down and left/right, and 2 numbers for the ratio that would delimitate the opening of the screen
  - As for the pixels, since the camera would be a point, the pixels would be rays hitting and the angles between the side vectors would be the x and y pixels. But this would mean having precise calculations => lots of rays going anywhere but the camera => lots of useless calculations => slow process
  - An optimisation could be to have a small sphere (/plane) instead of the point, but that would get the image blury I think

With these thoughts, I think I could be able to create the v1 of the raytracer/raycaster

## Versions

### V1

After trying with the "realistic" version of light going to the camera, I realised the number of rays would be increadibly high...

### V2

After this V1, the V2 now allows me to have a nice "working v1".

I managed to have spheres and planes (by extensions rectangles and blocks/cubes...).

I also managed to specify how reflective a surface is (though I might need to review that part, I think I overcomplicated it because of a previous bug)


Here is an example with a mirror Sphere, a "shiny" plane, 2 mat cubes (one in front and one behind the camera which can be seen in the ball)
![example_image.png](images%2Fexample_image.png)

Now added the pavement feature to easily create rubik's cubes
![example_rubikscube.png](images%2Fexample_rubikscube.png)