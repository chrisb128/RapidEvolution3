package com.mixshare.rapid_evolution.ui.model.profile.filter.style;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

import org.apache.log4j.Logger;

import com.mixshare.rapid_evolution.data.profile.filter.style.StyleProfile;
import com.mixshare.rapid_evolution.data.record.search.label.LabelRecord;
import com.mixshare.rapid_evolution.ui.model.ModelPopulatorInterface;
import com.mixshare.rapid_evolution.ui.model.column.Column;
import com.mixshare.rapid_evolution.ui.model.column.StaticTypeColumn;
import com.mixshare.rapid_evolution.ui.model.column.comparables.Percentage;
import com.mixshare.rapid_evolution.ui.model.profile.filter.FilterProfileLabelsModel;
import com.mixshare.rapid_evolution.ui.model.search.label.LabelModelManager;
import com.mixshare.rapid_evolution.ui.widgets.profile.filter.FilterTabTableItemModel;
import com.mixshare.rapid_evolution.util.io.LineReader;
import com.mixshare.rapid_evolution.util.io.LineWriter;
import com.trolltech.qt.core.QObject;

public class StyleLabelModelManager extends LabelModelManager {

    static private Logger log = Logger.getLogger(StyleLabelModelManager.class);	
    static private final long serialVersionUID = 0L;    
    
    static public StaticTypeColumn[] ALL_COLUMNS = {     	
		COLUMN_LABEL_NAME.getInstance(true),
		COLUMN_THUMBNAIL_IMAGE.getInstance(true),
		COLUMN_DEGREE.getInstance(true),
		COLUMN_RATING_STARS.getInstance(true),
		COLUMN_RATING_VALUE.getInstance(false),
		COLUMN_NUM_SONGS.getInstance(true),
		COLUMN_COMMENTS.getInstance(true),
		COLUMN_STYLES.getInstance(false),
		COLUMN_TAGS.getInstance(false),
		COLUMN_LAST_MODIFIED.getInstance(false)
    };
    
    ////////////
    // FIELDS //
    ////////////
    
    transient private FilterProfileLabelsModel profileLabelsModel;
    transient private StyleProfile relativeStyle;
    
    static {
    	try{
    		// in order for the XMLEncoder to skip transient variables, this is needed
    		BeanInfo info = Introspector.getBeanInfo(StyleLabelModelManager.class);
    		PropertyDescriptor[] propertyDescriptors = info.getPropertyDescriptors();
    		for (int i = 0; i < propertyDescriptors.length; ++i) {
    			PropertyDescriptor pd = propertyDescriptors[i];
    			if (pd.getName().equals("profileLabelsModel") || pd.getName().equals("relativeStyle")) {
    				pd.setValue("transient", Boolean.TRUE);
    			}
    		}
    	} catch (IntrospectionException e) {
    		log.error("static(): error", e);
    	}
    }
    
    /////////////////
    // CONSTRUCTOR //
    /////////////////
    
	public StyleLabelModelManager() { }
	
	public StyleLabelModelManager(LineReader lineReader) {
		super(lineReader);
		int version = Integer.parseInt(lineReader.getNextLine());
	}		
	
	public void initColumns() {
		sourceColumns.clear();
		for (Column column : ALL_COLUMNS)
			sourceColumns.add(column);		
	}
	
	protected void createSourceModel(QObject parent) {
		model = new FilterTabTableItemModel(getModelPopulator().getSize(), getNumColumns(), parent, this);
		loadTable();
	}
	
	/////////////
	// GETTERS //
	/////////////
	
	public StaticTypeColumn[] getAllStaticColumns() { return ALL_COLUMNS; }
	
	public String getTypeDescription() { return "Style Label"; }
	
	public Object getSourceData(short columnId, Object record) {
		if (log.isTraceEnabled())
			log.trace("getColumnData(): columnId=" + columnId + ", record=" + record);		
		LabelRecord labelRecord = (LabelRecord)record;
		// can insert model specific columns here
		if (columnId == COLUMN_DEGREE.getColumnId()) {
			return new Percentage(labelRecord.getActualStyleDegree(relativeStyle.getUniqueId()));
		}
		return super.getSourceData(columnId, record);
	}
	
	public ModelPopulatorInterface getModelPopulator() { return profileLabelsModel; }
	
	public boolean excludeExternalItems() { return false; }
	
	public boolean isLazySearchSupported() { return true; }
	
	/////////////
	// SETTERS //
	/////////////
	
	public void setRelativeStyle(StyleProfile relativeStyle) {
		profileLabelsModel = new FilterProfileLabelsModel(relativeStyle, this);
		this.relativeStyle = relativeStyle;
	}
	
	public void reset() {
		profileLabelsModel.update();
		super.reset();
	}		

	public void refresh() {
		if (log.isDebugEnabled())
			log.debug("refresh(): called");
		try {
			loadData(profileLabelsModel.getResults(), getSearchProxyModel().getSearchParameters());
		} catch (Exception e) {
			log.error("refresh(): error", e);
		}
	}	
	
	/////////////
	// METHODS //
	/////////////
	
	public void write(LineWriter writer) {
		super.write(writer);
		writer.writeLine(1); // version
	}	
	
}
