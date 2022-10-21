import { Column, CreateDateColumn, Entity, JoinColumn, JoinTable, ManyToMany, OneToMany, OneToOne, PrimaryGeneratedColumn } from 'typeorm';
import { Category } from './Category';
import { Image } from './Image';
import { NewsFeed } from './NewsFeed';
import { Season } from './Season';
import { UserSeries } from './UserSeries';

@Entity()
export class Series {
  @PrimaryGeneratedColumn()
  id: number;

  @Column({ nullable: false })
  title: string;

  @Column({
    type: 'year',
    nullable: false
  })
  prodYear: number;

  @Column({nullable: false})
  ageLimit: number;

  @Column({nullable: false})
  length: number; // in minutes

  @CreateDateColumn()
  added: string;

  @OneToMany(() => NewsFeed, feed => feed.series)
  newsfeeds: NewsFeed[];

  @OneToMany(() => Season, season => season.series, {
    cascade: ['insert', 'update'],
    onUpdate: 'CASCADE'
  })
  seasons: Season[]

  @ManyToMany(() => Category, category => category.serieses, {
    cascade: true
  })
  @JoinTable()
  categories: Category[]

  @OneToMany(() => UserSeries, feed => feed.series)
  userserieses: UserSeries[];

  @OneToOne(() => Image, image => image.series, {
    nullable: true,
    eager: true,
    cascade: true
  })
  @JoinColumn()
  image: Image;
}