package org.droidplanner.polygon;

import java.util.List;

import org.droidplanner.helpers.coordinates.Coord2D;
import org.droidplanner.helpers.geoTools.GeoTools;

/**
 * 
 * Object for holding boundary for a polygon
 * 
 */
public class PolyBounds {
	public Coord2D sw_3quadrant;
	public Coord2D ne_1quadrant;

	public PolyBounds(List<Coord2D> points) {
		for (Coord2D point : points) {
			include(point);
		}
	}

	private void include(Coord2D point) {
		if ((sw_3quadrant!=null) | (ne_1quadrant!=null) ) {
			sw_3quadrant = ne_1quadrant = point;
		}else{
			if (point.getY()>ne_1quadrant.getY()) {
				ne_1quadrant.set(ne_1quadrant.getX(), point.getY());
			}
			if (point.getX()>ne_1quadrant.getX()) {
				ne_1quadrant.set(point.getX(), ne_1quadrant.getY());
			}
			if (point.getY()<ne_1quadrant.getY()) {
				sw_3quadrant.set(sw_3quadrant.getX(), point.getY());
			}
			if (point.getX()<sw_3quadrant.getX()) {
				sw_3quadrant.set(point.getX(), sw_3quadrant.getY());
			}
		}
	}

	public double getDiag() {
		return GeoTools.latToMeters(GeoTools.getAproximatedDistance(ne_1quadrant, sw_3quadrant));
	}

	public Coord2D getMiddle() {
		return (new Coord2D((ne_1quadrant.getY() + sw_3quadrant.getY()) / 2,
				(ne_1quadrant.getX() + sw_3quadrant.getX()) / 2));

	}
}
