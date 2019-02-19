import React from 'react';
import { BrowserRouter, Route, Link } from "react-router-dom";
import clsx from 'clsx';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import Drawer from '@material-ui/core/Drawer';
import CssBaseline from '@material-ui/core/CssBaseline';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import List from '@material-ui/core/List';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';
import IconButton from '@material-ui/core/IconButton';
import ListItem from '@material-ui/core/ListItem';
import ListItemIcon from '@material-ui/core/ListItemIcon';
import ListItemText from '@material-ui/core/ListItemText';
import MenuIcon from '@material-ui/icons/Menu';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import InboxIcon from 'mdi-react/InboxIcon';

import Home from './pages/Home';
import SE01 from './pages/SE01';
import GeoRef from './pages/GeoRef';
import Rest from './pages/Rest';

const drawerWidth = 240;

const useStyles = makeStyles(theme => ({
    root: {
        display: 'flex',
    },
    appBar: {
        transition: theme.transitions.create(['margin', 'width'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
    },
    appBarShift: {
        width: `calc(100% - ${drawerWidth}px)`,
        marginLeft: drawerWidth,
        transition: theme.transitions.create(['margin', 'width'], {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.enteringScreen,
        }),
    },
    menuButton: {
        marginRight: theme.spacing(2),
    },
    hide: {
        display: 'none',
    },
    drawer: {
        width: drawerWidth,
        flexShrink: 0,
    },
    drawerPaper: {
        width: drawerWidth,
    },
    drawerHeader: {
        display: 'flex',
        alignItems: 'center',
        padding: '0 8px',
        ...theme.mixins.toolbar,
        justifyContent: 'flex-end',
    },
    content: {
        flexGrow: 1,
        padding: theme.spacing(3),
        transition: theme.transitions.create('margin', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
        marginLeft: -drawerWidth,
    },
    contentShift: {
        transition: theme.transitions.create('margin', {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.enteringScreen,
        }),
        marginLeft: 0,
    },
    homeLink: {
        color: 'white',
        textDecoration: 'none',
    },
}));

const SE_STORIES = [
    { to: '/rest/1', title: 'REST SE01' },
    { to: '/rest/2', title: 'REST SE02' },
    { to: '/rest/3', title: 'REST SE03' },
    { to: '/rest/4', title: 'REST SE04' },
    { to: '/rest/5', title: 'REST SE05' },
    { to: '/rest/6', title: 'REST SE06' },
    { to: '/rest/7', title: 'REST SE07' },
];

const SG_STORIES = [
    { to: '/georef/1', title: 'Geo Ref SG01' },
    { to: '/georef/2', title: 'Geo Ref SG02' },
    { to: '/georef/3', title: 'Geo Ref SG03' },
    { to: '/georef/4', title: 'Geo Ref SG04' },
    { to: '/georef/5', title: 'Geo Ref SG05' },
    { to: '/georef/6', title: 'Geo Ref SG06' },
    { to: '/georef/7', title: 'Geo Ref SG07' },
];

function App() {
    const classes = useStyles();
    const theme = useTheme();
    const [open, setOpen] = React.useState(false);

    function handleDrawerOpen() {
        setOpen(true);
    }

    function handleDrawerClose() {
        setOpen(false);
    }

    return (
        <BrowserRouter>
            <div className={classes.root}>
                <CssBaseline />
                <AppBar
                    position="fixed"
                    className={clsx(classes.appBar, {
                        [classes.appBarShift]: open,
                    })}>
                    <Toolbar>
                        <IconButton
                            color="inherit"
                            aria-label="Open drawer"
                            onClick={handleDrawerOpen}
                            edge="start"
                            className={clsx(classes.menuButton, open && classes.hide)}>
                            <MenuIcon />
                        </IconButton>
                        <Typography className={classes.homeLink} variant="h6" noWrap component={Link} to="/">LAPR4 Web Client</Typography>
                    </Toolbar>
                </AppBar>
                <Drawer
                    className={classes.drawer}
                    variant="persistent"
                    anchor="left"
                    open={open}
                    classes={{ paper: classes.drawerPaper }}>
                    <div className={classes.drawerHeader}>
                        <IconButton onClick={handleDrawerClose}>
                            {theme.direction === 'ltr' ? <ChevronLeftIcon /> : <ChevronRightIcon />}
                        </IconButton>
                    </div>
                    <Divider />
                    <List>
                        {SE_STORIES.map((link, index) => (
                            <ListItem button key={index} component={Link} to={link.to}>
                                <ListItemIcon>
                                    <InboxIcon />
                                </ListItemIcon>
                                <ListItemText primary={link.title} />
                            </ListItem>
                        ))}
                    </List>
                    <Divider />
                    <List>
                        {SG_STORIES.map((link, index) => (
                            <ListItem button key={index} component={Link} to={link.to}>
                                <ListItemIcon>
                                    <InboxIcon />
                                </ListItemIcon>
                                <ListItemText primary={link.title} />
                            </ListItem>
                        ))}
                    </List>
                </Drawer>
                <main
                    className={clsx(classes.content, {
                        [classes.contentShift]: open,
                    })}>
                    <div className={classes.drawerHeader} />
                    <Route path="/" exact component={Home} />
                    <Route path="/se01" component={SE01} />
                    <Route path="/georef/:id" component={GeoRef} />
                    <Route path="/rest/:id" component={Rest} />
                </main>
            </div>
        </BrowserRouter>
    );
}

export default App;