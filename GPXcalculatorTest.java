package edu.upenn.cis350.gpx;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class GPXcalculatorTest {

	//test for normal working conditions. If given 2 tracksegments that both moved 5 units of distance, total distance moved should be 10
	public void testNormalConditions()
	{
		GPXtrkpt one = new GPXtrkpt(0.0,0.0,null);
		GPXtrkpt two = new GPXtrkpt(3.0,4.0,null);
		ArrayList<GPXtrkpt> trkpt = new ArrayList<GPXtrkpt>();
		trkpt.add(one);
		trkpt.add(two);
		GPXtrkseg seg1 = new GPXtrkseg(trkpt);
		GPXtrkseg seg2 = new GPXtrkseg(trkpt);
		ArrayList<GPXtrkseg> trkseg = new ArrayList<GPXtrkseg>();
		trkseg.add(seg1);
		trkseg.add(seg2);
		GPXtrk trk = new GPXtrk(null,trkseg);
		assertEquals(GPXcalculator.calculateDistanceTraveled(trk),10.0,0.0);

	}
	
	@Test
	//if GPXtrk object is null, it should return -1
	public void testCalculateDistanceTraveledNull() {
		assertEquals(GPXcalculator.calculateDistanceTraveled(null),-1.0,0.0);
	}
	
	//if GPXtrk contains no GPXtrkseg obj, return -1
	public void testCalculateDistanceTraveledNotrk()
	{
		ArrayList<GPXtrkseg> empty = new ArrayList<GPXtrkseg>();
		GPXtrk test = new GPXtrk("test",empty);
		assertEquals(GPXcalculator.calculateDistanceTraveled(test),-1.0,0.0);
	}
	
	//if any seg in the trk is null, that seg should return 0
	//so a track with 2 segs, with one being null, should be equal as track with 1 same non-null seg
	public void testNullseg()
	{
		GPXtrkpt origin = new GPXtrkpt(0.0,0.0,null);
		GPXtrkpt point = new GPXtrkpt(3.0,4.0,null);
		ArrayList<GPXtrkpt> trkpt = new ArrayList<GPXtrkpt>();
		trkpt.add(point);
		trkpt.add(origin);
		GPXtrkseg valid = new GPXtrkseg(trkpt);
		ArrayList<GPXtrkseg> trkseg = new ArrayList<GPXtrkseg>();
		trkseg.add(valid);
		GPXtrk trk = new GPXtrk(null,trkseg);
		assertEquals(GPXcalculator.calculateDistanceTraveled(trk),5.0,0.0);
		
		trkseg.add(null);
		assertEquals(GPXcalculator.calculateDistanceTraveled(trk),5.0,0.0);

	}
	
	//if a seg contains no trkpt objects, distance for that seg should be 0
	//so a track with 2 segs, one seg that has distance 5 and one seg with no trkpt, should return total
	//distance of 5
	public void testNoTrkpt()
	{
		ArrayList<GPXtrkpt> empty = new ArrayList<GPXtrkpt>();
		GPXtrkseg testseg = new GPXtrkseg(empty);
		ArrayList<GPXtrkseg> list = new ArrayList<GPXtrkseg>();
		list.add(testseg);
		GPXtrk testtrk = new GPXtrk("test",list);
		assertEquals(GPXcalculator.calculateDistanceTraveled(testtrk),0.0,0.0);
		
		GPXtrkpt valid = new GPXtrkpt(3.0,4.0,null);
		GPXtrkpt origin = new GPXtrkpt(0.0,0.0,null);
		ArrayList<GPXtrkpt> nonempty = new ArrayList<GPXtrkpt>();
		nonempty.add(valid);
		nonempty.add(origin);
		GPXtrkseg testseg2 = new GPXtrkseg(nonempty);
		list.add(testseg2);
		assertEquals(GPXcalculator.calculateDistanceTraveled(testtrk),5.0,0.0);

	}
	
	//if a seg contains only one trkpt, distance for that seg should be 0;
	//a track with 2 segs, one seg with only 1 trkpt, the other seg with total distance 5
	//should return total distance 5
	public void testOneTrkpt()
	{
		GPXtrkpt test = new GPXtrkpt(3.0,6.0,null);
		ArrayList<GPXtrkpt> trkpt = new ArrayList<GPXtrkpt>();
		trkpt.add(test);
		GPXtrkseg testseg = new GPXtrkseg(trkpt);
		ArrayList<GPXtrkseg> list = new ArrayList<GPXtrkseg>();
		list.add(testseg);
		GPXtrk testtrk = new GPXtrk("test",list);
		assertEquals(GPXcalculator.calculateDistanceTraveled(testtrk),0.0,0.0);
		
		GPXtrkpt valid = new GPXtrkpt(3.0,4.0,null);
		GPXtrkpt origin = new GPXtrkpt(0.0,0.0,null);
		ArrayList<GPXtrkpt> trkpt2 = new ArrayList<GPXtrkpt>();
		trkpt2.add(valid);
		trkpt2.add(origin);
		GPXtrkseg testseg2 = new GPXtrkseg(trkpt2);
		list.add(testseg2);
		assertEquals(GPXcalculator.calculateDistanceTraveled(testtrk),5.0,0.0);
	}
	
	//if any trkpt is null, distance for that seg should be 0
	//a track with 2 segs, 1 having a null trkpt and 1 with length 5,
	//should have total length 5
	public void testNullTrkpt()
	{
		GPXtrkpt test = new GPXtrkpt(3.0,6.0,null);
		ArrayList<GPXtrkpt> trkpt = new ArrayList<GPXtrkpt>();
		trkpt.add(test);
		trkpt.add(null);
		GPXtrkseg testseg = new GPXtrkseg(trkpt);
		ArrayList<GPXtrkseg> list = new ArrayList<GPXtrkseg>();
		list.add(testseg);
		GPXtrk testtrk = new GPXtrk("test",list);
		
		GPXtrkpt valid = new GPXtrkpt(3.0,4.0,null);
		GPXtrkpt origin = new GPXtrkpt(0.0,0.0,null);
		ArrayList<GPXtrkpt> trkpt2 = new ArrayList<GPXtrkpt>();
		trkpt2.add(valid);
		trkpt2.add(origin);
		GPXtrkseg testseg2 = new GPXtrkseg(trkpt2);
		list.add(testseg2);
		assertEquals(GPXcalculator.calculateDistanceTraveled(testtrk),5.0,0.0);

	}
	
	//if trkpt has latitude >90 <-90, the distance of the seg should be 0
	//a track with 2 segs, 1 seg having a bad latitude, one with length 5
	//should have total length 5
	public void testbadLat()
	{
		GPXtrkpt test = new GPXtrkpt(99.0,6.0,null);
		GPXtrkpt badorigin = new GPXtrkpt(0.0,0.0,null);
		ArrayList<GPXtrkpt> trkpt = new ArrayList<GPXtrkpt>();
		trkpt.add(test);
		trkpt.add(badorigin);
		GPXtrkseg testseg = new GPXtrkseg(trkpt);
		ArrayList<GPXtrkseg> list = new ArrayList<GPXtrkseg>();
		list.add(testseg);
		GPXtrk testtrk = new GPXtrk("test",list);
		
		GPXtrkpt valid = new GPXtrkpt(3.0,4.0,null);
		GPXtrkpt origin = new GPXtrkpt(0.0,0.0,null);
		ArrayList<GPXtrkpt> trkpt2 = new ArrayList<GPXtrkpt>();
		trkpt2.add(valid);
		trkpt2.add(origin);
		GPXtrkseg testseg2 = new GPXtrkseg(trkpt2);
		list.add(testseg2);
		assertEquals(GPXcalculator.calculateDistanceTraveled(testtrk),5.0,0.0);
	}
	
	//if trkpt has longitude >180 or <-180, distance of seg should be 0
	//a track with 2 segs, 1 seg with bad longitude, one with length 5
	//should have total length 5
	public void testbadLong()
	{
		GPXtrkpt test = new GPXtrkpt(2.0,-200.0,null);
		GPXtrkpt badorigin = new GPXtrkpt(0.0,0.0,null);
		ArrayList<GPXtrkpt> trkpt = new ArrayList<GPXtrkpt>();
		trkpt.add(test);
		trkpt.add(badorigin);
		GPXtrkseg testseg = new GPXtrkseg(trkpt);
		ArrayList<GPXtrkseg> list = new ArrayList<GPXtrkseg>();
		list.add(testseg);
		GPXtrk testtrk = new GPXtrk("test",list);
		
		GPXtrkpt valid = new GPXtrkpt(3.0,4.0,null);
		GPXtrkpt origin = new GPXtrkpt(0.0,0.0,null);
		ArrayList<GPXtrkpt> trkpt2 = new ArrayList<GPXtrkpt>();
		trkpt2.add(valid);
		trkpt2.add(origin);
		GPXtrkseg testseg2 = new GPXtrkseg(trkpt2);
		list.add(testseg2);
		assertEquals(GPXcalculator.calculateDistanceTraveled(testtrk),5.0,0.0);
	}
	
}
