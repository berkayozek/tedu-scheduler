package com.teduscheduler.entity;

import com.teduscheduler.model.Section;

import java.util.ArrayList;
import java.util.List;

public class Timetable {
    private List<Section> sections;
    private boolean isConflict;
    private List<Section> conflictSections;

    public Timetable(){
        sections = new ArrayList<>();
        conflictSections = new ArrayList<>();
        isConflict = false;
    }

    public Timetable(Timetable timetable){
        this();
        clone(timetable);
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public boolean isConflict() {
        return isConflict;
    }

    public void setConflict(boolean conflict) {
        isConflict = conflict;
    }

    public List<Section> getConflictSections() {
        return conflictSections;
    }

    public void setConflictSections(List<Section> conflictSections) {
        this.conflictSections = conflictSections;
    }

    public void addConflictSections(Section ...sections){
        for (Section section : sections) {
            Section newSection = new Section();
            newSection.setSectionCode(section.getSectionCode());
            conflictSections.add(newSection);
        }
    }

    private void clone(Timetable timetable) {
        for (Section section:timetable.getSections()) {
            sections.add(section);
        }

        for (Section section : timetable.getConflictSections()) {
            conflictSections.add(section);
        }

        isConflict = timetable.isConflict();
    }
}
