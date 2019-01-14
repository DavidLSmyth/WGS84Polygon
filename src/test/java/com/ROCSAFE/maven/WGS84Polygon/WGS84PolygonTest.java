/**
 * 
 */
package com.ROCSAFE.maven.WGS84Polygon;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ROCSAFE.maven.gpsutilities.CoordinateBase;
import com.ROCSAFE.maven.gpsutilities.WGS84Coordinate;

/**
 * @author David Smyth
 *
 */
class WGS84PolygonTest {
	
	WGS84Coordinate p1;
	WGS84Coordinate p2;
	WGS84Coordinate p3;
	WGS84Coordinate p4;
	WGS84Coordinate p5;
	
	List<WGS84Coordinate> squareBoundary;
	WGS84Polygon square;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		//lat,long
		p1 = new WGS84Coordinate(0,0);
		p2 = new WGS84Coordinate(0.25,0.25);
		p3 = new WGS84Coordinate(0,0.5);
		p4 = new WGS84Coordinate(-0.25,0.25);
		p5 = new WGS84Coordinate(-0.125, -4);
		
		squareBoundary = new ArrayList<WGS84Coordinate>(Arrays.asList(p1,p2,p3,p4));
		square = new WGS84Polygon(squareBoundary);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.Polygon#Polygon(java.util.List)}.
	 */
	@Test
	void testPolygon() {
		//ensure that constuctor throws correct execption when too few vertices provided
		assertThrows(Exception.class, ()->new WGS84Polygon(Arrays.asList(p1)));
		assertThrows(Exception.class, ()->new WGS84Polygon(Arrays.asList(p1,p2)));
	}
	
	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.Polygon#equals(Object other)}.
	 * @throws Exception 
	 */
	@Test
	void testEquals() throws Exception {
		WGS84Polygon poly1 = new WGS84Polygon(new ArrayList<WGS84Coordinate>(Arrays.asList(p3,p1,p4,p2, p4)));
		WGS84Polygon poly2 = new WGS84Polygon(new ArrayList<WGS84Coordinate>(Arrays.asList(p3,p1,p4)));
		assertEquals(poly1, square);
		assertNotEquals(poly2, square);
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.Polygon#getBoundingQuadrilateral()}.
	 * @throws Exception 
	 */
	@Test
	//Ignore for now
	
	void testGetBoundingQuadrilateral() throws Exception {
		for(WGS84Coordinate c: square.getBoundingQuadrilateral().getBoundary()) {
			System.out.println(c);
		}
		WGS84Coordinate boundingQuadP1 = new WGS84Coordinate(0.25,0.5);
		WGS84Coordinate boundingQuadP2 = new WGS84Coordinate(-0.25,0.5);
		WGS84Coordinate boundingQuadP3 = new WGS84Coordinate(0.25,0);
		WGS84Coordinate boundingQuadP4 = new WGS84Coordinate(-0.25,0);
		WGS84Quadrilateral boundary = new WGS84Quadrilateral(boundingQuadP1,
				boundingQuadP2,
				boundingQuadP3,
				boundingQuadP4);
		for(CoordinateBase c: boundary.getBoundary()) {
			System.out.println(square.getBoundingQuadrilateral().getBoundary().contains(c));
		}
		//assertTrue(boundary.getBoundary().containsAll(square.getBoundingQuadrilateral().getBoundary()));
		//ensure that set of points defining bounding quadrilateral are equal
		assertEquals(new HashSet<CoordinateBase>(boundary.getBoundary()), new HashSet<CoordinateBase>(square.getBoundingQuadrilateral().getBoundary()));
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.Polygon#getBoundary()}.
	 * @throws Exception 
	 */
	@Test
	void testGetBoundary() throws Exception {
		assertEquals(new LinkedHashSet<CoordinateBase>(squareBoundary), square.getBoundary());
		squareBoundary.add(p5);
		square.setBoundary(new LinkedHashSet<WGS84Coordinate>(squareBoundary));
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.Polygon#getHighestLat()}.
	 * @throws Exception 
	 */
	@Test
	void testGetHighestLat() {
		assertEquals(p2.getY(), square.getHighestLat());
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.Polygon#getHighestLng()}.
	 */
	@Test
	void testGetHighestLng() {
		assertEquals(p3.getX(), square.getHighestLng());
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.Polygon#getLowestLng()}.
	 */
	@Test
	void testGetLowestLng() {
		assertEquals(p1.getX(), square.getLowestLng());
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.Polygon#getLowestLat()}.
	 */
	@Test
	void testGetLowestLat() {
		assertEquals(p4.getY(), square.getLowestLat());
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.Polygon#getHighestLatCoord()}.
	 * @throws Exception 
	 */
	@Test
	void testGetHighestLatCoord() {
		assertEquals(p2, square.getHighestLatCoord());
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.Polygon#getHighestLngCoord()}.
	 */
	@Test
	void testGetHighestLngCoord() {
		assertEquals(p3, square.getHighestLngCoord());		
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.Polygon#getLowestLngCoord()}.
	 */
	@Test
	void testGetLowestLngCoord() {
		assertEquals(p1, square.getLowestLngCoord());
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.Polygon#getLowestLatCoord()}.
	 */
	@Test
	void testGetLowestLatCoord() {
		assertEquals(p4, square.getLowestLatCoord());
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.Polygon#setBoundary(java.util.List)}.
	 */
	@Test
	void testSetBoundary() {
		assertThrows(Exception.class, ()->square.setBoundary(new LinkedHashSet<WGS84Coordinate>(Arrays.asList(p1, p2))));
		assertThrows(Exception.class, ()->square.setBoundary(new LinkedHashSet<WGS84Coordinate>(Arrays.asList(p1))));
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.Polygon#toString()}.
	 */
	@Test
	void testToString() {
		assertEquals(p1.toString() + "\n" +
				p2.toString() + "\n" + 
				p3.toString() + "\n" +
				p4.toString(),square.toString());
		//fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.ROCSAFE.maven.WGS84Polygon.Polygon#pointInPolygon(com.ROCSAFE.maven.gpsutilities.WGS84Coordinate)}.
	 * @throws Exception 
	 */
	@Test
	void testPointInPolygon() throws Exception {
		assertTrue(square.pointInPolygon(new WGS84Coordinate(0,0.1)));
		assertFalse(square.pointInPolygon(new WGS84Coordinate(5,0.1)));
		assertFalse(square.pointInPolygon(new WGS84Coordinate(-5,0.1)));
		assertFalse(square.pointInPolygon(new WGS84Coordinate(0.25,3)));
		assertFalse(square.pointInPolygon(new WGS84Coordinate(0.25,-33)));
	}

}
