package controller;

public class Point {

	public int pointx, pointy;

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
