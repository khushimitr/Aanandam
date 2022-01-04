const mongoose = require('mongoose');
const Hotelservice = require('../models/hotelService');

const seedHotelServices = [
    {
    serviceName: 'Hospital Visit',
    images: ['https://images.pexels.com/photos/3814571/pexels-photo-3814571.jpeg',
    'https://images.pexels.com/photos/3768146/pexels-photo-3768146.jpeg',
    'https://images.pexels.com/photos/7581074/pexels-photo-7581074.jpeg',
    'https://images.pexels.com/photos/8088852/pexels-photo-8088852.jpeg'],
    price: {
        premium: 0,
        regular: 250
    },
    accomodations: [
        'Assistant',
        'Car facility',
        'Allowance'
    ],
    details: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.',
    description: 'Believe us!'
    },
    {
    serviceName: 'Function Visit',
    images: ['https://images.pexels.com/photos/5779170/pexels-photo-5779170.jpeg',
    'https://images.pexels.com/photos/5774928/pexels-photo-5774928.jpeg',
    'https://images.pexels.com/photos/5778894/pexels-photo-5778894.jpeg',
    'https://images.pexels.com/photos/6184770/pexels-photo-6184770.jpeg'],
    price: {
        premium: 0,
        regular: 300
    },
    accomodations: [
        'Assistant',
        'Car facility'
    ],
    details: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.',
    description: 'Enjoy without worries!'
    },
    {
    serviceName: 'Bank Visit',
    images: ['https://images.pexels.com/photos/7821710/pexels-photo-7821710.jpeg',
    'https://images.pexels.com/photos/518244/pexels-photo-518244.jpeg',
    'https://images.pexels.com/photos/8353786/pexels-photo-8353786.jpeg',
    'https://images.pexels.com/photos/3823540/pexels-photo-3823540.jpeg'],
    price: {
        premium: 0,
        regular: 250
    },
    accomodations: [
        'Assistant',
        'Car facility'
    ],
    details: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.',
    description: 'Mangage your finances!'
    },
    {
    serviceName: 'Marriage Visit',
    images: ['https://images.pexels.com/photos/1456613/pexels-photo-1456613.jpeg',
    'https://images.pexels.com/photos/3407978/pexels-photo-3407978.jpeg',
    'https://images.pexels.com/photos/3831156/pexels-photo-3831156.jpeg',
    'https://images.pexels.com/photos/10488867/pexels-photo-10488867.jpeg'],
    price: {
        premium: 0,
        regular: 350
    },
    accomodations: [
        'Assistant',
        'Car facility'
    ],
    details: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.',
    description: 'Never miss a marriage!'
    },
    {
    serviceName: 'Saloon Visit',
    images: ['https://images.pexels.com/photos/4245297/pexels-photo-4245297.jpeg',
    'https://images.pexels.com/photos/6743347/pexels-photo-6743347.jpeg',
    'https://images.pexels.com/photos/4783264/pexels-photo-4783264.jpeg',
    'https://images.pexels.com/photos/4783260/pexels-photo-4783260.jpeg',
    'https://images.pexels.com/photos/3356174/pexels-photo-3356174.jpeg'],
    price: {
        premium: 0,
        regular: 250
    },
    accomodations: [
        'Assistant',
        'Car facility'
    ],
    details: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.',
    description: 'Grooming is important!'
    },
    {
    serviceName: 'Market Visit',
    images: ['https://images.pexels.com/photos/439818/pexels-photo-439818.jpeg',
    'https://images.pexels.com/photos/696205/pexels-photo-696205.jpeg',
    'https://images.pexels.com/photos/945982/pexels-photo-945982.jpeg',
    'https://images.pexels.com/photos/375896/pexels-photo-375896.jpeg'],
    price: {
        premium: 0,
        regular: 250
    },
    accomodations: [
        'Assistant',
        'Car facility'
    ],
    details: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.',
    description: 'Get your groceries!'
    },
    {
    serviceName: 'Maintenance',
    images: ['https://images.pexels.com/photos/5691590/pexels-photo-5691590.jpeg',
    'https://images.pexels.com/photos/3811829/pexels-photo-3811829.jpeg',
    'https://images.pexels.com/photos/3768910/pexels-photo-3768910.jpeg',
    'https://images.pexels.com/photos/4867360/pexels-photo-4867360.jpeg'],
    price: {
        premium: 0,
        regular: 300
    },
    accomodations: [
        'Assistant',
        'Car facility'
    ],
    details: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.',
    description: 'Get work done!'
    },
    {
    serviceName: 'Office Visit',
    images: ['https://images.pexels.com/photos/3823490/pexels-photo-3823490.jpeg',
    'https://images.pexels.com/photos/3823494/pexels-photo-3823494.jpeg',
    'https://images.pexels.com/photos/3782192/pexels-photo-3782192.jpeg',
    'https://images.pexels.com/photos/5668799/pexels-photo-5668799.jpeg'],
    price: {
        premium: 0,
        regular: 300
    },
    accomodations: [
        'Assistant',
        'Car facility'
    ],
    details: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.',
    description: 'Paper work!'
    },
    {
    serviceName: 'Babyshower Visit',
    images: ['https://images.pexels.com/photos/2091331/pexels-photo-2091331.jpeg',
    'https://images.pexels.com/photos/7802477/pexels-photo-7802477.jpeg',
    'https://images.pexels.com/photos/6134654/pexels-photo-6134654.jpeg',
    'https://images.pexels.com/photos/8909917/pexels-photo-8909917.jpeg'],
    price: {
        premium: 0,
        regular: 350
    },
    accomodations: [
        'Assistant',
        'Car facility'
    ],
    details: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.',
    description: 'Bless the newborn!'
    },
    {
    serviceName: 'Court Visit',
    images: ['https://images.pexels.com/photos/4427909/pexels-photo-4427909.jpeg',
    'https://images.pexels.com/photos/6538438/pexels-photo-6538438.jpeg',
    'https://images.pexels.com/photos/4427543/pexels-photo-4427543.jpeg',
    'https://images.pexels.com/photos/5668791/pexels-photo-5668791.jpeg'],
    price: {
        premium: 0,
        regular: 400
    },
    accomodations: [
        'Assistant',
        'Car facility'
    ],
    details: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.',
    description: 'Your honour!'
    },
    {
    serviceName: 'Funeral Visit',
    images: ['https://images.pexels.com/photos/3648309/pexels-photo-3648309.jpeg',
    'https://images.pexels.com/photos/7317705/pexels-photo-7317705.jpeg',
    'https://images.pexels.com/photos/7317741/pexels-photo-7317741.jpeg',
    'https://images.pexels.com/photos/7317696/pexels-photo-7317696.jpeg'],
    price: {
        premium: 0,
        regular: 0
    },
    accomodations: [
        'Assistant',
        'Car facility'
    ],
    details: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus ac luctus arcu. Maecenas eget pretium ante. Mauris ornare tortor non nunc ultrices luctus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Mauris felis nibh, congue a ornare eget, commodo sed odio. Aliquam erat volutpat. Quisque et malesuada mauris. Ut dictum magna eu interdum sollicitudin. Integer sollicitudin felis eu metus lobortis tincidunt. Nam eget convallis enim, ut sagittis leo. Etiam blandit nibh molestie diam tincidunt convallis. Vestibulum nulla sapien, finibus eu odio eget, sodales rhoncus nisi. Donec at purus eget enim porttitor ullamcorper. Nulla egestas rhoncus arcu, vel aliquam ante hendrerit tincidunt.',
    description: 'Condolences'
    }
];

const addHotelServices = async () => {
    await Hotelservice.deleteMany({});
    const result = await Hotelservice.insertMany(seedHotelServices);
    console.log(result);
} 

module.exports = addHotelServices;