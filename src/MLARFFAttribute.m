//
//  MLARFFAttribute.m
//  ml
//
//  Created by Jack Maloney on 5/17/14.
//
//

#import "MLARFFAttribute.h"

@implementation MLARFFAttribute

- (instancetype)initWithName:(NSString *)name
{
    self = [super init];
    if (self) {
        self.name = name;
    }
    return self;
}

-(void) addData:(id)data {
    [self.data addObject:data];
}

@end
