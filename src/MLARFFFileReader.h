//
//  MLFileReader.h
//  ml
//
//  Created by Jack Maloney on 5/17/14.
//
//

#import <Foundation/Foundation.h>

@interface MLARFFFileReader : NSObject

@property (nonatomic, readonly) NSString* path;

@property (nonatomic, readonly) NSMutableArray* attributes;

@property (nonatomic, readonly) NSString* relation;

@property (nonatomic, readonly, strong) NSArray* data;

-(instancetype) initWithPath:(NSString*) path;
-(void) parse;

@end
