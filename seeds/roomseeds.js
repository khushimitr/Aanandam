const mongoose = require('mongoose');
const Room = require('../models/room'); 

const seedRooms = [
    {
        roomId: 101,
        roomName: "Beas",
        details: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.",
        roomtype: "1BHK",
        location: "Saharanpur",
        accomodations: ["Wifi", "Induction", "Laundry", "Mess", "Gym", "Service"],
        images: ["https://images.pexels.com/photos/10397031/pexels-photo-10397031.jpeg",
        "https://images.pexels.com/photos/8251701/pexels-photo-8251701.jpeg",
        "https://images.pexels.com/photos/90317/pexels-photo-90317.jpeg",
        "https://images.pexels.com/photos/271624/pexels-photo-271624.jpeg"],
        maxPerson: 1,
        cost: [5999, 69999]
    },
    {
        roomId: 102,
        roomName: "Narmada",
        details: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.",
        roomtype: "2BHK",
        location: "Saharanpur",
        accomodations: ["Wifi", "Induction", "Laundry", "Mess", "Gym", "Service"],
        images: ["https://images.pexels.com/photos/6585759/pexels-photo-6585759.jpeg",
        "https://images.pexels.com/photos/6782430/pexels-photo-6782430.jpeg",
        "https://images.pexels.com/photos/279746/pexels-photo-279746.jpeg",
        "https://images.pexels.com/photos/6890413/pexels-photo-6890413.jpeg"],
        maxPerson: 2,
        cost: [6799, 56789]
    },
    {
        roomId: 103,
        roomName: "Bhramaputra",
        details: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.",
        roomtype: "1BHK",
        location: "Saharanpur",
        accomodations: ["Wifi", "Induction", "Laundry", "Mess", "Gym", "Service"],
        images: ["https://images.pexels.com/photos/6588581/pexels-photo-6588581.jpeg",
        "https://images.pexels.com/photos/7214473/pexels-photo-7214473.jpeg",
        "https://images.pexels.com/photos/6970069/pexels-photo-6970069.jpeg",
        "https://images.pexels.com/photos/6207463/pexels-photo-6207463.jpeg"],
        maxPerson: 1,
        cost: [5699, 76799]
    },
    {
        roomId: 104,
        roomName: "Jhelum",
        details: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.",
        roomtype: "2BHK",
        location: "Saharanpur",
        accomodations: ["Wifi", "Induction", "Laundry", "Mess", "Gym", "Service"],
        images: ["https://images.pexels.com/photos/5997968/pexels-photo-5997968.jpeg",
        "https://images.pexels.com/photos/271739/pexels-photo-271739.jpeg",
        "https://images.pexels.com/photos/2255424/pexels-photo-2255424.jpeg",
        "https://images.pexels.com/photos/210604/pexels-photo-210604.jpeg"],
        maxPerson: 2,
        cost: [6899, 64569]
    },
    {
        roomId: 105,
        roomName: "Yamuna",
        details: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.",
        roomtype: "1BHK",
        location: "Saharanpur",
        accomodations: ["Wifi", "Induction", "Laundry", "Mess", "Gym", "Service"],
        images: ["https://images.pexels.com/photos/271643/pexels-photo-271643.jpeg",
        "https://cdn.pixabay.com/photo/2019/08/19/13/58/bed-4416515__340.jpg",
        "https://images.pexels.com/photos/6434632/pexels-photo-6434632.jpeg",
        "https://images.pexels.com/photos/271624/pexels-photo-271624.jpeg"],
        maxPerson: 1,
        cost: [7899, 65899]
    },
    {
        roomId: 106,
        roomName: "Godavari",
        details: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.",
        roomtype: "1BHK",
        location: "Saharanpur",
        accomodations: ["Wifi", "Induction", "Laundry", "Mess", "Gym", "Service"],
        images: ["https://images.pexels.com/photos/271624/pexels-photo-271624.jpeg",
        "https://images.pexels.com/photos/7061089/pexels-photo-7061089.jpeg",
        "https://images.pexels.com/photos/6434632/pexels-photo-6434632.jpeg",
        "https://images.pexels.com/photos/271643/pexels-photo-271643.jpeg"],
        maxPerson: 2,
        cost: [6499, 76999]
    },
    {
        roomId: 107,
        roomName: "Tapi",
        details: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.",
        roomtype: "2BHK",
        location: "Saharanpur",
        accomodations: ["Wifi", "Induction", "Laundry", "Mess", "Gym", "Service"],
        images: ["https://images.pexels.com/photos/271619/pexels-photo-271619.jpeg",
        "https://images.pexels.com/photos/7061089/pexels-photo-7061089.jpeg",
        "https://images.pexels.com/photos/6434632/pexels-photo-6434632.jpeg",
        "https://images.pexels.com/photos/271643/pexels-photo-271643.jpeg"],
        maxPerson: 1,
        cost: [6899, 67899]
    },
    {
        roomId: 201,
        roomName: "Ravi",
        details: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.",
        roomtype: "1BHK",
        location: "Saharanpur",
        accomodations: ["Wifi", "Induction", "Laundry", "Mess", "Gym", "Service"],
        images: ["https://images.pexels.com/photos/164595/pexels-photo-164595.jpeg",
        "https://images.pexels.com/photos/3659683/pexels-photo-3659683.jpeg",
        "https://images.pexels.com/photos/3682238/pexels-photo-3682238.jpeg",
        "https://images.pexels.com/photos/2029722/pexels-photo-2029722.jpeg"],
        maxPerson: 2,
        cost: [7899, 61499]
    },
    {
        roomId: 202,
        roomName: "Krishna",
        details: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.",
        roomtype: "2BHK",
        location: "Saharanpur",
        accomodations: ["Wifi", "Induction", "Laundry", "Mess", "Gym", "Service"],
        images: ["https://images.pexels.com/photos/3754595/pexels-photo-3754595.jpeg",
        "https://images.pexels.com/photos/8862294/pexels-photo-8862294.jpeg",
        "https://images.pexels.com/photos/8112351/pexels-photo-8112351.jpeg",
        "https://images.pexels.com/photos/6862448/pexels-photo-6862448.jpeg"],
        maxPerson: 2,
        cost: [6199, 62399]
    },
    {
        roomId: 203,
        roomName: "Cauveri",
        details: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.",
        roomtype: "1BHK",
        location: "Saharanpur",
        accomodations: ["Wifi", "Induction", "Laundry", "Mess", "Gym", "Service"],
        images: ["https://images.pexels.com/photos/6636288/pexels-photo-6636288.jpeg",
        "https://images.pexels.com/photos/6862448/pexels-photo-6862448.jpeg",
        "https://images.pexels.com/photos/6636288/pexels-photo-6636288.jpeg",
        "https://images.pexels.com/photos/10415971/pexels-photo-10415971.jpeg"],
        maxPerson: 1,
        cost: [5499, 53299]
    },
    {
        roomId: 204,
        roomName: "Chenab",
        details: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.",
        roomtype: "2BHK",
        location: "Saharanpur",
        accomodations: ["Wifi", "Induction", "Laundry", "Mess", "Gym", "Service"],
        images: ["https://images.pexels.com/photos/8112351/pexels-photo-8112351.jpeg",
        "https://images.pexels.com/photos/6862448/pexels-photo-6862448.jpeg",
        "https://images.pexels.com/photos/6636288/pexels-photo-6636288.jpeg",
        "https://images.pexels.com/photos/10415971/pexels-photo-10415971.jpeg"],
        maxPerson: 2,
        cost: [6099, 59899]
    },
    {
        roomId: 205,
        roomName: "Sutlej",
        details: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.",
        roomtype: "1BHK",
        location: "Saharanpur",
        accomodations: ["Wifi", "Induction", "Laundry", "Mess", "Gym", "Service"],
        images: ["https://images.pexels.com/photos/9825991/pexels-photo-9825991.jpeg",
        "https://cdn.pixabay.com/photo/2016/04/02/19/18/bed-1303450__340.jpg",
        "https://cdn.pixabay.com/photo/2019/08/19/13/58/bed-4416515__340.jpg",
        "https://cdn.pixabay.com/photo/2014/10/16/08/39/bedroom-490779__340.jpg"],
        maxPerson: 2,
        cost: [8999, 79899]
    },
    {
        roomId: 206,
        roomName: "Chambal",
        details: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.",
        roomtype: "2BHK",
        location: "Saharanpur",
        accomodations: ["Wifi", "Induction", "Laundry", "Mess", "Gym", "Service"],
        images: ["https://cdn.pixabay.com/photo/2016/04/02/19/18/bed-1303450__340.jpg",
        "https://cdn.pixabay.com/photo/2019/08/19/13/58/bed-4416515__340.jpg",
        "https://cdn.pixabay.com/photo/2014/10/16/08/39/bedroom-490779__340.jpg",
        "https://cdn.pixabay.com/photo/2016/07/08/23/36/beach-house-1505461__340.jpg"],
        maxPerson: 2,
        cost: [7699, 75599]
    },
    {
        roomId: 207,
        roomName: "Kosi",
        details: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.",
        roomtype: "1BHK",
        location: "Saharanpur",
        accomodations: ["Wifi", "Induction", "Laundry", "Mess", "Gym", "Service"],
        images: ["https://images.pexels.com/photos/2029722/pexels-photo-2029722.jpeg",
        "https://images.pexels.com/photos/3754595/pexels-photo-3754595.jpeg",
        "https://images.pexels.com/photos/8862294/pexels-photo-8862294.jpeg",
        "https://images.pexels.com/photos/8112351/pexels-photo-8112351.jpeg"],
        maxPerson: 2,
        cost: [6789, 64799]
    }
];

const addRooms = async () => {
    await Room.deleteMany({});
    const result = await Room.insertMany(seedRooms);
    console.log(result);
} 

module.exports = addRooms;