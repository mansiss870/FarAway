package rdProject.utilities;
public class MouseLocation implements java.io.Serializable
{
private int x;
private int y;
public MouseLocation(int x,int y)
{
this.x=x;
this.y=y;
}
public void setX(int x)
{
this.x=x;
}
public int getX()
{
return this.x;
}
public void setY(int y)
{
this.y=y;
}
public int getY()
{
return this.y;
}
}
