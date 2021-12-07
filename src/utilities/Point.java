package utilities;

public class Point {

	public int pointx, pointy;

	//This is a constructor for the Point.java class containing point x and y on a cartesian plane.
	public Point(int pointx, int pointy) {
		this.pointx = pointx;
		this.pointy = pointy;
	}

	public int getPointx() {
		return pointx;
	}

	public void setPointx(int pointx) {
		this.pointx = pointx;
	}

	public int getPointy() {
		return pointy;
	}

	public void setPointy(int pointy) {
		this.pointy = pointy;
	}

	@Override
	public String toString() {
		return "Point [pointx=" + pointx + ", pointy=" + pointy + "]";
	}

}