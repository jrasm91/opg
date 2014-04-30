Quick overview of the state of the code. April 2011

Package Summary: 
 opg						contains the main methods where the program starts
 opg.chart					defines interface for the charts types
 opg.chart.cmds				draw commands that are used to draw charts
 opg.chart.circ				circular pedigree chart
 opg.chart.vertical  		pedigree/descendant chart, the main chart for the beta software - minor bugs
 opg.chart.selectvertical 	pedigree chart that selects box of different heights until the tree fits on a page - work in progress / descendancy not implemented
 opg.chart.presetvertical	pedigree chart that uses preset boxes/formats - current work, not fully implemented, descendancy not implemented
 opg.chart.working			full pedigree chart, used to fill information in by hand
 opg.color					color schemes
 opg.conf					user configurations that are persistent
 opg.exec					definition of different opg exceptions
 opg.fonts					embedded fonts - only a few are actually used
 opg.gui					panels and dialogs - main gui code
 opg.tools					various gui elements
 opg.image					embedded images
 opg.io						classes related to input/output chart writers, gedcom parser, etc.
 opg.model					model objects - mostly for representing pedigree/familes
 opg.nfs					New Family Search integration code
 opg.util					various utility classes - name abbreviator,browser launcher, etc. 
 
 
There can be several chart types that can be selected in the software.
Each chart has a class that implements ChartMaker, which is called to create the chart. 

 * When making a new chart type you should do the following
 * 1) Create a new package for your new chart type in opg.chart (e.g. opg.chart.NEWCHARTTYPE), place all new classes in this package
 * 2) Create a Chart Maker class which implements the ChartMaker interface
 * 3) Create an options class which extends ChartOptions and put any additional options for 
 *    your chart type in that class
 * 4) Create a class which extends SpecificOptionsPanel and has any buttons/boxes/labels/logic ect
 *    needed to populate the options class you created
 * 5) Add your chart type to the opg.model.ChartType enum with appropriate reference to your chartmaker
 *    class.
 * 
 * If you don't need any chart specific options then you are free to use the default TypeSpecificOptions
 * and SpecificOptionsPanel
 * 
 
 