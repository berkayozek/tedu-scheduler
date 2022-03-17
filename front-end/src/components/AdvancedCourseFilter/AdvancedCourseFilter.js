import { Button, Dialog, DialogActions, DialogContent, DialogTitle, IconButton, Switch } from "@material-ui/core";
import React, { useState } from "react"
import { SettingsSharp } from '@material-ui/icons';
import "./AdvancedCourseFilter.scss"
import FormControl from "@material-ui/core/FormControl";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";


const AdvancedCourseFilter = ({ advancedFilter, updateAdvancedFilter }) => {
    const [showAdvancedFilter, setShowAdvancedFilter] = useState(false)

    const flags = {
        "allowConflict" : "Allow conflicts",
        "emptyOneDay" : "Show at least one day empty"
    }

    const openAdvancedFilter = () => {
        setShowAdvancedFilter(true)
    }

    const closeAdvancedFilter = () => {
        setShowAdvancedFilter(false)
    }

    const flagOnChanged = (flagType) => {
        console.log(advancedFilter)
        updateAdvancedFilter(flagType, !advancedFilter[flagType])
    }

    return(
        <>
            <div className={"advanced-filter"}>
                <IconButton onClick={openAdvancedFilter}>
                    <SettingsSharp className={"filter-button"}  />
                </IconButton>
            </div>
            <Dialog
                fullWidth={ true }
                open={showAdvancedFilter}
                maxWidth={"xs"}
                onClose={closeAdvancedFilter}
            >
                <DialogTitle>Advanced Filter</DialogTitle>
                <DialogContent>
                    <div className={"filter-list"}>
                        <div key={"allowConflict"} className={"filter"}>
                            <div className={"filter-name"}>
                                Allow conflicts
                            </div>
                            <div>
                                <Switch
                                    checked={advancedFilter.allowConflict}
                                    onChange={(e) => flagOnChanged("allowConflict", e.target.value)}
                                    color="primary"
                                    size={"small"}
                                />
                            </div>
                        </div>
                        <div key={"emptyDay"} className={"filter"}>
                            <div className={"filter-name"}>
                                Show at least {advancedFilter.emptyDayCount === 1 ? "one" : "two"} day empty
                            </div>
                            <div className={"filter-buttons"}>
                                <FormControl className={"empty-day-select"}>
                                    <Select
                                        labelId="empty-day-select-label"
                                        value={advancedFilter.emptyDayCount}
                                        disabled={!advancedFilter.emptyDay}
                                        onChange={(e) => updateAdvancedFilter("emptyDayCount", parseInt(e.target.value))}
                                    >
                                        <MenuItem value={1}>1</MenuItem>
                                        <MenuItem value={2}>2</MenuItem>
                                    </Select>
                                </FormControl>
                                <Switch
                                    checked={advancedFilter.emptyDay}
                                    onChange={() => flagOnChanged("emptyDay")}
                                    color="primary"
                                    size={"small"}
                                />
                            </div>
                        </div>
                        <div key={"sectionRoom"} className={"filter"}>
                            <div className={"filter-name"}>
                                Show sections' room
                            </div>
                            <div>
                                <Switch
                                    checked={advancedFilter.showRoom}
                                    onChange={(e) => flagOnChanged("showRoom", e.target.value)}
                                    color="primary"
                                    size={"small"}
                                />
                            </div>
                        </div>
                    </div>
                </DialogContent>
                <DialogActions>
                    <Button onClick={closeAdvancedFilter} color="primary">
                        Close
                    </Button>
                </DialogActions>
            </Dialog>
        </>

    )
}

export default AdvancedCourseFilter