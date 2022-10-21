import { Column, Entity, OneToOne, PrimaryGeneratedColumn } from 'typeorm';
import { Series } from './Series';

@Entity()
export class Image {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  name: string;

  @Column({
    default: "0px"
  })
  x_offset?: string;

  @Column({
    default: "0px"
  })
  y_offset?: string;

  @OneToOne(() => Series, series => series.image)
  series: Series;
}