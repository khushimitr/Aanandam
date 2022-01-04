require('dotenv').config();
const express = require('express');
const app = express();
const User = require('./models/user');
const Room = require('./models/room');
const Hotelservice = require('./models/hotelService');
const Premiumuser = require('./models/premiumUser');
const Employee = require('./models/employee');
const Leave = require('./models/leaves');
const Service = require('./models/service');
const bcrypt = require('bcrypt');
const mongoose = require('mongoose');
const jwt = require('jsonwebtoken');
const addRooms = require('./seeds/roomseeds');
const addHotelServices = require('./seeds/hotelServiceSeeds');
const addEmployeeUsers = require('./seeds/userEmployeeSeeds');
const service = require('./models/service');
const cors = require('cors');
const dbUrl = process.env.DB_URL || 'mongodb://localhost:27017/anandamProject';
app.use(express.json());
app.use(cors({
    origin: "*"
}))

mongoose.connect(dbUrl, {useNewUrlParser: true, useUnifiedTopology: true})
.then(() => {
    console.log('Mongo connection established!');
})
.catch((err) => {
    console.log('Mongo connection Error!');
    console.log(err);
})

function authenticateToken(req, res, next){
    const {accessToken} = req.body;
    console.log(accessToken);

    if(accessToken == null)
    {
         return res.send('token null hai');
    }

    jwt.verify(accessToken, process.env.ACCESS_TOKEN_SECRET, (err, {email}) => {
         if(err)
         {
             return res.send('error hai');
         }
         req.user = {email};
         next();
    })
}

//Login/Signup routes----------------------------------------------------------------------------------------------------------

app.post('/register', async (req, res) => {
    //code to enter the info of the new user into the database
    const user = new User(req.body);    
    const {email, password} = req.body;
    const dbentry = await User.findOne({email: email});
    if(dbentry)
    {
        console.log(dbentry);
        return res.json({accessToken: null, success: false, user: false});
    }
    hashedPass = await bcrypt.hash(password, 12);
    user.password = hashedPass;
    user.dateJoined = Date.now();
    if(!user.isEmployee)
    {
        user.isEmployee = false;
    }
    await user.save();

    //after adding the info of the user into the database we will provide the access token to the user.
    const accessToken = jwt.sign({email}, process.env.ACCESS_TOKEN_SECRET)
    return res.json({accessToken: accessToken, success: true, user});
})

app.post('/employee/login', async (req, res) => {
    const {email, password} = req.body;
    const user = await User.findOne({email: email});
    if(!user)
    {
        return res.json({accessToken: null, success: false, employee: null, services: []});
    }
    else{
        if(user.isEmployee)
        {
            //if i have found the user, now I will authenticate the user
            const isValid = await bcrypt.compare(password, user.password);
            if(isValid){
                //user is successfully authenticated
                const accessToken = jwt.sign({email}, process.env.ACCESS_TOKEN_SECRET)
                const employee = await Employee.findOne({user: user._id}).populate('user');
                //console.log(employee);
                let date = new Date();
                let nextDate = new Date(date.getFullYear(), date.getMonth(), date.getDate()+7);
                let services = await Service.find({employee: employee._id, "dateInfo.servingDateStart": {$lte: nextDate}}).populate('user').populate('hotelService').populate('employee');
                if(services.length === 0)
                {   
                    services = [];
                }
                console.log(services);
                return res.json({accessToken: accessToken, 
                success: true, employee, services});
            }
            else{
                return res.json({accessToken: null, success: false, employee: null, services: []});
            }
        }
        else{
            return res.json({accessToken: null, success: false, employee: null, services: []});
        }
    }
})

app.post('/login', async(req, res) => {
    //code to authenticate user
    const {email, password} = req.body;
    const user = await User.findOne({email: email});
    if(!user)
    {
        return res.json({accessToken: null, success: false, user: null});
    }
    else{
        //if i have found the user, now I will authenticate the user
        const isValid = await bcrypt.compare(password, user.password);
        if(isValid){
            //user is successfully authenticated
            const accessToken = jwt.sign({email}, process.env.ACCESS_TOKEN_SECRET)
            return res.json({accessToken: accessToken, success: true, user});
        }
        else{
            return res.json({accessToken: null, success: false, user: null});
        }
    }
})


//------------------------------------------------------Room Routes-------------------------------------------------------------------------------
app.get('/rooms', async (req, res) => {
    console.log(req.query);
    if(!req.query.type && !req.query.maxperson)
    {
        const rooms = await Room.find({isVacant: true}, {roomId: 1, roomName: 1, location: 1, roomtype: 1, images: 1, isVacant: 1, cost: 1, maxPerson: 1});
        console.log('---------------------------------');
        console.log(rooms);
        return res.json({rooms: rooms, success: true});
    }
    else
    {
        if(req.query.type)
        {
            const {type} = req.query;
            if(type.length > 5)
            {
                if(req.query.maxperson)
                {
                    let {maxperson} = req.query;
                    maxperson = parseInt(maxperson);
                    const rooms = await Room.find({maxPerson: {$lte: maxperson}, isVacant: true})
                    return res.json({rooms: rooms, success: true});
                }
                else{
                    const rooms = await Room.find({isVacant: true}, {roomId: 1, roomName: 1, location: 1, roomtype: 1, images: 1, isVacant: 1, cost: 1, maxPerson: 1});
                    console.log('---------------------------------');
                    console.log(rooms);
                    return res.json({rooms: rooms, success: true});
                }
            }
            else
            {
                if(req.query.maxperson)
                {
                    let {maxperson} = req.query;
                    maxperson = parseInt(maxperson);
                    const rooms = await Room.find({roomtype: type,maxPerson: {$lte: maxperson},isVacant: true})
                    return res.json({rooms: rooms, success: true});
                }
                else
                {
                    const rooms = await Room.find({roomtype: type, isVacant: true});
                    return res.json({rooms: rooms, success: true});
                }
            }
        }
    }
})

app.get('/rooms/:id', async (req, res) => {
    const {id} = req.params;
    const room = await Room.findById(id);
    console.log('--------------------------------------------');
    return res.json({room: room, success: true});
})


//----------------------------------------------------Hotel Service Routes-------------------------------------------------------------------------
app.get('/hotelservices' , async(req, res) => {
    const hotelServices = await Hotelservice.find({}, {serviceName: 1, images: 1, description: 1});
    console.log('-------------');
    console.log(hotelServices);
    return res.json({success: true, hotelServices: hotelServices});
})

app.get('/hotelservices/:id', async(req, res) => {
    const {id} = req.params;
    const hotelService = await Hotelservice.findById(id);
    console.log(hotelService);
    return res.json({success: true, hotelService: hotelService});
})

//------------------------------------------------------Service Routes----------------------------------------------------------------------
app.post('/services', authenticateToken, async (req, res) => {
    //take user from the token
    const user = await User.findOne({email: req.user.email});
    user.contact = req.body.teleNumber;
    const hotelService = await Hotelservice.findOne({serviceName: req.body.type});
    const services = await Service.find({$or: [{"dateInfo.servingDateStart": {$gt: req.body.servingDateEnd}}, {"dateInfo.servingDateEnd": {$lt: req.body.servingDateStart}}]});
    console.log(services);
    let targetServicesIds = [];
    for(let service of services)
    {
        targetServicesIds.push(service._id);
    }
    const targetServices = await Service.find({_id : {$nin: targetServicesIds}});
    console.log('-----------------------------------------------------------');
    console.log(targetServices);
    let nonEligibleEmployees=[];
    for(let targetService of targetServices)
    {
        nonEligibleEmployees.push(targetService.employee);
    }
    const employee = await Employee.findOne({_id: {$nin: nonEligibleEmployees}});
    if(!employee)
    {
        return res.json({success: false, service: null});
    }
    console.log('----------------------------------------------------------------');
    console.log(employee); 
    user.availedServices += 1;
    await user.save();
    let service = new Service({
        user: user,
        employee: employee,
        hotelService: hotelService,
        address: {pickUp: req.body.pickUpAddress, destination: req.body.destinationAddress},
        description: req.body.description,
        dateInfo: {servingDateStart: req.body.servingDateStart, servingDateEnd: req.body.servingDateEnd} //service date span
        //services dates 
    })
    console.log(service);
    const id = service._id;
    await service.save();
    service = await Service.findById(id).populate('user').populate({path: 'employee', populate: {path: 'user'}}).populate('hotelService');
    console.log(service);
    return res.json({success: true, service: service});
})

app.post('/services/info', authenticateToken, async(req, res) => {
    const user = await User.findOne({email: req.user.email});
    const services = await Service.find({user: user._id}).populate({path: 'employee', populate: {path: 'user'}}).populate('hotelService');
    console.log(services);
    return res.json({success: true, services: services});
})

//---------------------------------------------------User routes----------------------------------------------------------------------------
app.post('/users', authenticateToken, async (req, res) => {
    const user = await User.findOne({email: req.user.email});
    return res.json({success: true, user: user});
})

app.put('/users', authenticateToken, async(req, res) => {
    const {email} = req.body;
    const user = await User.findOne({email: req.user.email});
    user.email = req.body.email;
    user.username = req.body.username;
    user.contact = req.body.teleNumber;
    user.image = req.body.profileImage;
    user.address = req.body.address;
    await user.save();

    const accessToken = jwt.sign({email}, process.env.ACCESS_TOKEN_SECRET);
    return res.json({accessToken: accessToken, success: true, user});
})

//---------------------------------------------------Premium User routes-------------------------------------------------------------------
app.post('/premiumusers', authenticateToken, async(req, res) => {
    console.log(req.body);
    const user = await User.findOne({email: req.user.email});
    user.contact = req.body.teleNumber;
    user.address = req.body.address;
    user.isPremium = true;
    console.log(user);
    await user.save();
    const room = await Room.findOne({roomId: req.body.roomId});
    room.isVacant = false;
    await room.save();
    let premiumUser = new Premiumuser({
        isRental: req.body.isRental,
        dateInfo: {checkIn: req.body.checkInDate, checkOut: req.body.checkOutDate},
        user: user,
        room: room,
        rentalFee: 6000
    })
    const id = premiumUser._id;
    const result = await premiumUser.save();
    premiumUser = await Premiumuser.findById(id).populate('user').populate('room');
    console.log(premiumUser);
    if(result)
    {
        return res.json({success: true, premiumUser});    
    } 
})

app.post('/premiumusers/info', authenticateToken, async(req, res) => {
    const user = await User.findOne({email: req.user.email});
    const premiumUser = await Premiumuser.findOne({user: user._id}).populate('room').populate('user');
    return res.json({success: true, premiumUser: premiumUser});
})

//-------------------------------------------------------Leave Paths------------------------------------------------------------------------
app.post('/leaves', authenticateToken, async(req, res) => {
    const user = await User.findOne({email: req.user.email});
    console.log(user);
    const employee = await Employee.findOne({user: user._id});
    console.log(employee);
    const leave = new Leave({
        employee: employee,
        dateInfo: {
            from: req.body.date.from,
            to: req.body.date.to
        },
        title: req.body.title,
        description: req.body.description
    })
    const result = await leave.save();
    if(result)
    {
        return res.json({success: true});
    }
})

//-------------------------------------------------------Admin Paths------------------------------------------------------------------------
const makeEmployees = async(req, res, next) => {
    addEmployeeUsers().then(() => {
    console.log('EmployeeUsers data base populated!');
    next();
});
}

app.post('/employees', makeEmployees, async(req, res) => {
    Employee.deleteMany({});
    const users = await User.find({isEmployee: true});
    let tempEmp;
    for(let user of users)
    {
        tempEmp = new Employee({user: user, salary: 6000})
        await tempEmp.save();
    }
    res.json({success: true});
})

app.get('/employees', async(req, res) => {
    const employees = await Employee.find().populate('user');
    res.json(employees);
})

app.get('/dates', (req, res) => {
    console.log(req.body);
    let date = new Date();
    let nextDate = new Date(date.getFullYear(), date.getMonth(), date.getDate()+7)
    console.log(date);
    res.send('cool!');
})

app.post('/seedrooms', async(req, res) => {
    addRooms().then(() => {
    console.log('Rooms data base populated!');
    res.json({success: true});
    });
})

app.post('/seedhotelservices', async(req, res) => {
    addHotelServices().then(() => {
            console.log('HotelService data base populated!');
            res.json({success: true});
        });
})

app.delete('/deleteall', async(req, res) => {
    await User.deleteMany({});
    await Employee.deleteMany({});
    await Service.deleteMany({});
    await Hotelservice.deleteMany({});
    await Room.deleteMany({});
    await Premiumuser.deleteMany({});
    await Leave.deleteMany({});
    res.json({success: true});
})

app.post('/seedall', makeEmployees, async(req, res) => {
    //seeding employees
    Employee.deleteMany({});
    const users = await User.find({isEmployee: true});
    let tempEmp;
    for(let user of users)
    {
        tempEmp = new Employee({user: user, salary: 6000})
        await tempEmp.save();
    }
    console.log('Employee data base populated!');
    
    //seeding hotelservices
    addHotelServices().then(() => {
        console.log('HotelService data base populated!');
    });

    //seeding rooms
    addRooms().then(() => {
        console.log('Rooms data base populated!');
    });

    res.json({success: true});
})

const port = process.env.PORT || 3000;
app.listen(port, () => {
    console.log(`${port} Server Listening...`);
})